package com.quan.windsleeve.service.impl;

import com.quan.windsleeve.bo.OrderCancelBO;
import com.quan.windsleeve.core.enums.OrderStatus;
import com.quan.windsleeve.model.OrderSku;
import com.quan.windsleeve.model.Orders;
import com.quan.windsleeve.repository.OrderRepository;
import com.quan.windsleeve.repository.SkuRepository;
import com.quan.windsleeve.service.IOrderCancelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderCancelServiceImpl implements IOrderCancelService {

    private final Logger log = LoggerFactory.getLogger(OrderCancelServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SkuRepository skuRepository;

    @Transactional
    @EventListener
    @Override
    public void revertStock(OrderCancelBO orderCancelBO) {
        System.out.println("返还库存方法接收到了事件");
        Long userId = orderCancelBO.getUserId();
        Long orderId = orderCancelBO.getOrderId();

        //根据userid和orderId确定唯一一个订单
        Optional<Orders> optional = orderRepository.findOneByIdAndUserId(orderId,userId);
        //如果当前Optional中有值
        if(optional.isPresent()) {
            Orders order = optional.get();
           Integer status =  order.getStatus();
           //需要判断当前订单是否已经支付，如果未支付，那么才需要执行去除库存操作
            if(!status.equals(OrderStatus.UNPAID.getCode())) {
                System.out.println("当前订单不是待支付状态，无法执行去库存操作...... status = "+status);
                return;
            }
            List<OrderSku> orderSkuList = order.getSnapItems();
            //返还库存 操作sku表
            if(orderSkuList != null) {
                orderSkuList.forEach(sku->{
                    try{
                        skuRepository.revertStock(sku.getId(),sku.getCount());
                    }catch (Exception e) {
                        log.error("返还库存操作失败，skuId = "+sku.getId().toString());
                    }

                });
                System.out.println("返还库存成功");
            }
            //修改订单的状态
            try {
                orderRepository.updateOrderStatus(orderId,userId);
            }catch (Exception e) {
                log.error("修改订单状态失败 orderId = "+orderId.toString()+" userId = "+userId.toString());
            }
            System.out.println("修改订单状态成功");
        }else {
            log.error("当前订单不存在，无法执行返还库存操作 orderId = "+orderId.toString());
        }
    }
}
