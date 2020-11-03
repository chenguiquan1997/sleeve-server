package com.quan.windsleeve.service.impl;

import com.quan.windsleeve.bo.OrderSkuBO;
import com.quan.windsleeve.bo.PageCounter;
import com.quan.windsleeve.core.configuration.OrderCheckerConfiguration;
import com.quan.windsleeve.core.enums.OrderStatus;
import com.quan.windsleeve.dto.OrderDTO;
import com.quan.windsleeve.dto.SkuInfoDTO;
import com.quan.windsleeve.exception.http.*;
import com.quan.windsleeve.logic.OrderChecker;
import com.quan.windsleeve.manager.IOrderDelayMessage;
import com.quan.windsleeve.model.Orders;
import com.quan.windsleeve.model.OrderSku;
import com.quan.windsleeve.model.Sku;
import com.quan.windsleeve.repository.*;
import com.quan.windsleeve.service.IOrderService;
import com.quan.windsleeve.util.OrderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SkuRepository skuRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Autowired
    private SpuRepository spuRepository;

    @Autowired
    private OrderCheckerConfiguration orderCheckerConfiguration;

    @Autowired
    private IOrderDelayMessage orderDelayMessage;

    @Value("${missyou.order.pay-limit-time}")
    private Long payLimitTime;

    /**
     * 当前方法用于校验订单
     * @param userId 用户id
     * @param orderDTO 前端传入的订单数据
     * @return
     */
    @Override
    public OrderChecker isOK(Long userId, OrderDTO orderDTO) {

        //验证用户id是否为空
        if(userId == null) {
            throw new ParameterException(70001);
        }

        //验证前端传入的当前订单应付总金额，是否小于0
        if(orderDTO.getTotalPrice().compareTo(new BigDecimal(0.00)) <= 0) {
            throw new ParameterException(70002);
        }

        //获取当前订单的sku集合
        List<SkuInfoDTO> skuInfoDTOList = orderDTO.getSkuInfoList();

        //获取所有SkuInfo的id
        List<Long> skuIdList = new ArrayList<>();
        skuInfoDTOList.forEach(skuInfoDTO -> {
            if(skuInfoDTO.getId() == null) {
                throw new AttributeException(70003);
            }
            skuIdList.add(skuInfoDTO.getId());
        });

        //根据skuIdList查询所有Sku
        List<Sku> skuList = skuRepository.findAllByIdIn(skuIdList);

        //根据skuList 与 List<SkuInfoDTO> 来生成 orderSkuBOList
        List<OrderSkuBO> orderSkuBOList = new ArrayList<>();
        //根据skuList 与 List<SkuInfoDTO> 来生成 orderSkuList
        List<OrderSku> orderSkuList = new ArrayList<>();

        skuList.forEach(sku -> {
            skuInfoDTOList.forEach(skuInfoDTO -> {
                if(sku.getId() == skuInfoDTO.getId()) {
                    OrderSkuBO orderSkuBO = new OrderSkuBO(skuInfoDTO,sku);
                    OrderSku orderSku = new OrderSku(sku,skuInfoDTO);
                    orderSkuBOList.add(orderSkuBO);
                    orderSkuList.add(orderSku);
                }
            });
        });


        //用户每进行一次下单操作，那么就会创建一个OrderChecker对象
        OrderChecker orderChecker = orderCheckerConfiguration.getOrderCheckerObject();
        System.out.println("1-orderDto: "+orderDTO);
        orderChecker.setOrderDTO(orderDTO);
        System.out.println("2-skuList: "+skuList);
        orderChecker.setServerSkuList(skuList);
        System.out.println("3-orderSkuBOList: "+orderSkuBOList);
        orderChecker.setOrderSkuBOList(orderSkuBOList);
        orderChecker.setUserId(userId);
        System.out.println("4-orderSkuList: "+orderSkuList);
        orderChecker.setOrderSkuList(orderSkuList);
        orderChecker.orderIsOK();

        return orderChecker;
    }

    /**
     * 当前方法用于创建用户订单
     * @param userId
     * @param orderChecker
     * @param orderDTO
     */
    @Transactional
    public Long createOrder(Long userId,OrderChecker orderChecker,OrderDTO orderDTO) {
        String orderNO = OrderUtils.getOrderNo();
        Date expireTime = OrderUtils.createOrderExpireTime(this.payLimitTime);
        Orders newOrder = Orders.builder()
                .userId(userId)
                .orderNo(orderNO)
                .finalTotalPrice(orderDTO.getFinalTotalPrice())
                .snapImg(orderChecker.getLeaderImg(orderChecker.getServerSkuList()))
                .snapTitle(orderChecker.getLeaderTitle(orderChecker.getServerSkuList()))
                .status(OrderStatus.UNPAID.getCode())
                .totalPrice(orderDTO.getTotalPrice())
                .totalCount(orderChecker.getCurrOrderTotalSkuCount(orderDTO))
                .expireTime(expireTime)
                .placedTime(new Date())
                .build();

        newOrder.setSnapAddress(orderDTO.getAddress());
        System.out.println("获取到的地址："+orderDTO.getAddress());
        newOrder.setSnapItems(orderChecker.getOrderSkuList());
        System.out.println("获取到的SnapItems: "+orderChecker.getOrderSkuList());

        //将数据保存到订单表
        orderRepository.save(newOrder);
        Long orderId = newOrder.getId();
        System.out.println(orderId);

        //减库存
        reduceStock(orderChecker);

        //如果当前订单使用了优惠券，那么需要进行优惠券的核销工作
        Long couponId = orderChecker.getOrderDTO().getCouponId();
        if(couponId != null) {
            //核销优惠券
            cancelAfterVerificationCoupon(userId,couponId,orderId);

        }
        //将消息发送到redis或mq
        orderDelayMessage.sendOrderDelayMessage(userId,orderId,couponId);
        return orderId;
    }





    /**
     * 进行减库存操作，有两种方式：
     * (1)利用数据库Innodb引擎自带的行锁特性->一条记录在被修改时，自动加锁，其他线程无法修改
     * (2)利用乐观锁的思想，它默认在读取数据的时候，其他线程不会对当前数据进行修改，所以读取数据
     *    时不会加锁，只有在对当前记录进行修改时，才会加锁。如果利用乐观锁的思想去解决减库存时的
     *    并发问题，那么需要对当前所有的记录增加版本号，在修改记录的时候，需要将版本号进行对比，
     *    如果版本号一致，那么可以进行修改操作。否则不可以。
     */
    public void reduceStock(OrderChecker orderChecker) {
        List<OrderSku> orderSkuList = orderChecker.getOrderSkuList();
        orderSkuList.forEach(orderSku -> {
            int result = skuRepository.reduceStock(orderSku.getId(),orderSku.getCount());
            if(result != 1) {
                throw new StockException(50005);
            }
        });
    }

    /**
     * 核销优惠券
     * @param userId
     * @param couponId
     * @param orderId
     */
    public void cancelAfterVerificationCoupon(Long userId,Long couponId,Long orderId) {
        int result = userCouponRepository
                .cancelAfterVerificationCoupon(userId,couponId,orderId);
        if(result != 1) {
            throw new CouponException(50006);
        }
    }

    /**
     * 获取待付款订单
     * @param userId
     * @param status
     * @param pageCounter
     * @return
     */
    @Override
    public Page<Orders> findWaitPayOrders(Long userId, Integer status, PageCounter pageCounter) {
        Date now = new Date();
        Pageable pageable = PageRequest.of(pageCounter.getPageNum(),pageCounter.getPageSize(),
                Sort.by("createTime").descending());
        Page<Orders> orderPage = orderRepository.findWaitPayOrders(now,userId,status,pageable);
        return orderPage;
    }

    /**
     * 获取当前用户的所有订单
     * @param userId
     * @param pageCounter
     * @return
     */
    @Override
    public Page<Orders> findAllOrders(Long userId, PageCounter pageCounter) {
        Pageable pageable = PageRequest.of(pageCounter.getPageNum(),pageCounter.getPageSize(),
                Sort.by("createTime").descending());
        Page<Orders> ordersPage = orderRepository.findAllByUserId(userId,pageable);
        return ordersPage;
    }

    /**
     * 根据状态获取相应订单
     * @param userId
     * @param status
     * @param pageCounter
     * @return
     */
    @Override
    public Page<Orders> findOrdersByStatus(Long userId, Integer status, PageCounter pageCounter) {
        Pageable pageable = PageRequest.of(pageCounter.getPageNum(),pageCounter.getPageSize());
        Page<Orders> ordersPage = orderRepository.findByUserIdAndStatusOrderByCreateTimeDesc(userId,status,pageable);
        return ordersPage;
    }

    /**
     * 如果当前订单过期了，用户还没有支付，那么需要执行返还库存的操作
     * @param key
     */
    @Override
    public void returnOfInventory(String key) {
        String[] strings = key.split(":");
        Long userId = Long.valueOf(strings[0]);
        Long orderId = Long.valueOf(strings[1]);
        Long couponId = Long.valueOf(strings[2]);

        if(userId != null && orderId != null && couponId != -1L) {
            //退库存和退优惠券操作，都执行
            Optional<Orders> optional = orderRepository.findOneByIdAndUserId(orderId,userId);
            Orders order = optional.orElseThrow(()->new NotFoundException(50012));
            List<OrderSku> orderSkuList = order.getSnapItems();
        }
    }

    /**
     * 将订单状态从“未支付”-->“已支付”
     * @param orderId
     * @param userId
     */
    @Override
    @Transactional
    public void updateOrderStatusToAlreadyPay(Long orderId, Long userId) {
        int result = orderRepository.updateOrderStatusToAlreadyPay(orderId,userId);
        if(result != 1) {
            //todo 表示修改订单状态失败，应该记录到一张专门的表中，后续进行修改
            System.out.println("当前订单状态修改失败，orderId="+orderId);
        }
    }


}