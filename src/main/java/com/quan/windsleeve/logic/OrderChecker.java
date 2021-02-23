package com.quan.windsleeve.logic;

import com.quan.windsleeve.bo.OrderSkuBO;
import com.quan.windsleeve.dto.OrderDTO;
import com.quan.windsleeve.dto.SkuInfoDTO;
import com.quan.windsleeve.exception.http.MoneyNotEqualsException;
import com.quan.windsleeve.exception.http.ResultValidateException;
import com.quan.windsleeve.model.OrderSku;
import com.quan.windsleeve.model.Sku;
import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 该类用于校验用户订单
 */
@Getter
@Setter
public class OrderChecker {

    /**
     * ordersDTO 和 serverSkuList 需要在外部手动通过setter()进行赋值
     */
    private OrderDTO orderDTO;
    //服务端根据当前订单中，skuId集合获取到的Sku集合
    private List<Sku> serverSkuList;
    //orderDTO 与 serverSkuList 整理之后的集合
    private List<OrderSkuBO> orderSkuBOList;
    private CouponChecker couponChecker;

    private Long userId;
    //服务端计算的当前订单，应付的总金额（没有扣除优惠券金额）
    private BigDecimal serverTotalPrice;

    //服务端查询出来的Sku 与 前端传入的Sku 整合之后的，服务于Order的类
    private List<OrderSku> orderSkuList = new ArrayList<>();

    /**
     * 获取当前订单中，第一个sku商品的图片，作为当前订单在前端页面显示的图片
     * @param skuList
     * @return
     */
    public String getLeaderImg(List<Sku> skuList) {
        return this.serverSkuList.get(0).getImg();
    }

    /**
     * 获取当前订单中，第一个sku商品的title，作为当前订单在前端页面显示的title
     * @param skuList
     * @return
     */
    public String getLeaderTitle(List<Sku> skuList) {
        return this.serverSkuList.get(0).getTitle();
    }

    /**
     * 获取当前订单中的所有 sku 数量总和
     * @param orderDTO
     * @return
     */
    public Integer getCurrOrderTotalSkuCount(OrderDTO orderDTO) {
        List<SkuInfoDTO> skuInfoDTOList = this.orderDTO.getSkuInfoList();
        Integer totalCount = 0;
        for(SkuInfoDTO s : skuInfoDTOList) {
            Integer count = s.getCount();
            totalCount += count;
        }
        return totalCount;
    }

    /**
     * 构造方法
     * @param couponChecker
     */
    public OrderChecker(CouponChecker couponChecker) {
        this.couponChecker = couponChecker;
    }

    /**
     * 当前方法用于：用户校验订单操作
     */
    public void orderIsOK() {
        //服务端计算的总金额与客户端传入的总金额进行比较
        BigDecimal serverTotalPrice = compareTotalPrice(this.orderDTO);
        this.serverTotalPrice = serverTotalPrice;
        System.out.println("5-服务端计算的总金额与客户端传入的总金额相等");

        //校验当前订单中，是否有下架的sku
        skuIsOnSale(this.orderDTO,this.orderSkuBOList);
        System.out.println("6-当前订单中，无sku下架");

        //校验当前订单中的商品是否售罄
        skuIsOutOfStock(this.serverSkuList);
        System.out.println("7-当前订单中，无商品售罄");

        //校验当前订单中，每个sku购买的数量，是否大于当前sku的库存数量
        buySkuCountIsOutOfCurrentSkuStock(this.orderDTO,this.serverSkuList);
        System.out.println("8-当前订单中，每个sku购买的数量，都大于当前sku的库存数量");

        //获取couponId
        Long couponId = this.orderDTO.getCouponId();
        //couponId != null 证明用户使用了优惠券，需要进行优惠券的验证
        if(couponId != null) {
            System.out.println("9-当前订单使用了优惠券");
            //优惠券本身属性校验，是否过期，是否可用
            this.couponChecker.checkCoupon(this.userId,this.orderDTO.getCouponId());
            System.out.println("10-优惠券属性校验通过");
            //优惠券使用门槛校验
            this.couponChecker.couponIsArriveThreshold(this.orderSkuBOList,this.serverTotalPrice);
            System.out.println("11-优惠券门槛校验通过");
            //校验除去优惠券的金额后，服务端计算的总金额与前端计算的总金额是否一致
            this.couponChecker.finalTotalPriceIsOk(this.orderDTO.getFinalTotalPrice(),this.serverTotalPrice);
            System.out.println("12-除去优惠券的金额后，服务端计算的总金额与前端计算的总金额一致");
        }else {
            compareFinalTotalPrice(this.serverTotalPrice,orderDTO.getFinalTotalPrice());
        }



    }

    /**
     * 当前订单如果没有使用优惠券，那么当前订单的应付金额，就是实付金额
     * @param serverTotalPrice
     * @param clientFinalTotalPrice
     */
    private void compareFinalTotalPrice(BigDecimal serverTotalPrice, BigDecimal clientFinalTotalPrice) {
        if(!serverTotalPrice.equals(clientFinalTotalPrice)) {
            throw new MoneyNotEqualsException(50001);
        }
    }

    /**
     * 校验当前订单中，每个sku购买的数量，是否大于当前sku的库存数量
     * @param orderDTO
     */
    public void buySkuCountIsOutOfCurrentSkuStock(OrderDTO orderDTO, List<Sku> serverSkuList) {
       List<SkuInfoDTO> skuInfoDTOList = orderDTO.getSkuInfoList();
       skuInfoDTOList.forEach(skuInfoDTO -> {
           serverSkuList.forEach(sku -> {
               if(skuInfoDTO.getId() == sku.getId()) {
                   Integer clientSkuCount = skuInfoDTO.getCount();
                   Integer serverSkuCount = sku.getStock();
                   if(serverSkuCount < clientSkuCount) {
                       throw new ResultValidateException(50004);
                   }
               }
           });
       });
    }

    /**
     * 校验当前订单中的商品是否售罄
     * @param serverSkuList 服务端根据当前订单计算的sku集合
     */
    public void skuIsOutOfStock(List<Sku> serverSkuList) {
        List<Object> sellOutList = new ArrayList<>();
        serverSkuList.forEach(sku -> {
            if(sku.getStock() == 0) {
                sellOutList.add(sku.getTitle());
            }
        });
        if(sellOutList.size() > 0) {
            String message = "已售罄商品名称："+sellOutList.toString();
            throw new ResultValidateException(50003,message);
        }
    }

    /**
     * 校验当前订单中，是否有下架的sku
     * @param orderDTO
     */
    public void skuIsOnSale(OrderDTO orderDTO,List<OrderSkuBO> orderSkuBOList) {
        int clientSkuCount = orderDTO.getSkuInfoList().size();
        int serverSkuCount = orderSkuBOList.size();
        /**
         * 如果客户端传入的sku数量与服务端从数据库获取的sku数量不一致，那么证明当前订单中
         * 有sku已经下架
         */
        if(clientSkuCount != serverSkuCount) {
            List<Object> underSaleList = new ArrayList<>();
            List<SkuInfoDTO> skuInfoDTOList = orderDTO.getSkuInfoList();
            for(SkuInfoDTO s : skuInfoDTOList) {
                if(containsOf(s.getId(),orderSkuBOList)) {
                    continue;
                }
               underSaleList.add(s.getId());
            }
            String message = "下架的sku id = "+underSaleList.toString();
            throw new ResultValidateException(50002,message);
        }
    }

    /**
     * 判断当前订单中，是否存在与id相等的sku
     * @param id
     * @param orderSkuBOList
     * @return
     */
    private boolean containsOf(Long id, List<OrderSkuBO> orderSkuBOList) {
        if(orderSkuBOList == null) return false;
        for(OrderSkuBO o : orderSkuBOList) {
            if(id.equals(o.getId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 服务端计算的总金额与客户端传入的总金额进行比较
     * @param orderDTO
     */
    public BigDecimal compareTotalPrice(OrderDTO orderDTO) {
        BigDecimal clientTotalPrice = orderDTO.getTotalPrice();

        //服务端计算的当前订单应付的总金额
        BigDecimal serverTotalPrice = currentOrderShouldPayTotalPrice(this.orderSkuBOList);
        if(!serverTotalPrice.equals(clientTotalPrice)) {
            System.out.println("服务端计算的金额："+serverTotalPrice);
            System.out.println("客户端传入的金额："+clientTotalPrice);
            throw new ResultValidateException(50001);
        }
        return serverTotalPrice;
    }

    /**
     * 服务端计算的当前订单应付的总金额
     * @param orderSkuBOList 服务端整合的当前订单sku集合
     */
    private BigDecimal currentOrderShouldPayTotalPrice(List<OrderSkuBO> orderSkuBOList) {
        BigDecimal shouldPayTotalPrice = new BigDecimal("0.00");
        for (OrderSkuBO orderSkuBO : orderSkuBOList) {
            BigDecimal skuShouldPayTotalPrice = new BigDecimal("0.00");
            BigDecimal actualPrice = orderSkuBO.getActualPrice();
            Integer count = orderSkuBO.getCount();
            skuShouldPayTotalPrice = actualPrice.multiply(new BigDecimal(count));
            shouldPayTotalPrice = shouldPayTotalPrice.add(skuShouldPayTotalPrice);
        }
        System.out.println("serverTotalPrice: "+shouldPayTotalPrice);
        return shouldPayTotalPrice;
    }

}
