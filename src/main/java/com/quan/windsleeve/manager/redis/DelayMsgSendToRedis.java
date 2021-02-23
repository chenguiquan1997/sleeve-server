package com.quan.windsleeve.manager.redis;

import com.quan.windsleeve.manager.IOrderDelayMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Primary
public class DelayMsgSendToRedis implements IOrderDelayMessage {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${missyou.order.pay-limit-time}")
    private Long payLimitTime;

    @Override
    public void sendOrderDelayMessage(Long userId, Long orderId, Long couponId) {
        if(couponId != null) {
            //将数据写入redis[1]号数据库中
            sendToRedis1(userId,orderId,couponId);
            System.out.println("有优惠券写入成功");
        }else {
            sendToRedis1(userId,orderId,-1L);
            System.out.println("无优惠券写入成功");
        }
    }

    /**
     * 向redis中发送订单数据
     * @param userId
     * @param orderId
     * @param couponId
     */
    private void sendToRedis1(Long userId,Long orderId,Long couponId) {
        String key = userId.toString()+":"+orderId.toString()+":"+couponId.toString();
        String value = "sleeve";
        try {
            //TimeUnit指的是：payLimitTime的单位
            stringRedisTemplate.opsForValue().set(key,value,payLimitTime, TimeUnit.SECONDS);
        }catch (Exception e) {
            /**
             * 在此不应该抛出异常,因为createOrder中的所有操作，是在同一个事务中的，如果有一处出现异常
             * 那么会导致整体用户下单操作失败，以为这是程序内部的错误，所以这是不应该的
             * 我们可以记录日志，或者写入到预警系统
             */
            System.out.println("订单数据插入Redis数据库异常");
        }
    }
}
