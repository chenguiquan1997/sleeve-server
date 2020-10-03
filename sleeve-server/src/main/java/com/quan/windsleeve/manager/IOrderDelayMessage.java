package com.quan.windsleeve.manager;

/**
 * 当前接口是实现策略模式的接口，用于mq和redis两个实现-->订单延迟支付的业务逻辑
 */
public interface IOrderDelayMessage {

    void sendOrderDelayMessage(Long userId,Long orderId,Long couponId);
}
