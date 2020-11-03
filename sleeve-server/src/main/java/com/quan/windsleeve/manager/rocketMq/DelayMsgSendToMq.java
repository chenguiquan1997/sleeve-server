package com.quan.windsleeve.manager.rocketMq;

import com.quan.windsleeve.manager.IOrderDelayMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
//@Primary
public class DelayMsgSendToMq implements IOrderDelayMessage {

    @Autowired
    private ScheduleProducer scheduleProducer;

    @Override
    public void sendOrderDelayMessage(Long userId, Long orderId, Long couponId) {
        String orderMsg = null;
        if(couponId == null) {
            orderMsg = userId.toString() +":"+ orderId.toString() + ":-1";
        }else {
            orderMsg = userId.toString() +":"+ orderId.toString()+":" + couponId.toString();
        }
        scheduleProducer.sendMsg(orderId.toString(),orderMsg);
    }
}
