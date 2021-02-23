package com.quan.windsleeve.manager.redis;

import com.quan.windsleeve.bo.OrderCancelBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class TopicManagerListener implements MessageListener {


    private static ApplicationEventPublisher publisher;

    @Autowired
    public void setPublisher(ApplicationEventPublisher publisher) {
        TopicManagerListener.publisher = publisher;
    }

    /**
     * 当前方法用于接收Redis的键空间通知
     * @param message
     * @param bytes
     */
    @Override
    public void onMessage(Message message, byte[] bytes) {
        byte[] body = message.getBody();
        byte[] channel = message.getChannel();
        String keyMsg = new String(body);
        String channelMsg = new String(channel);
        System.out.println("keyMsg数据："+keyMsg);
        System.out.println("channelMsg数据："+channelMsg);
        OrderCancelBO orderCancelBO = new OrderCancelBO(keyMsg);
        //事件发布器发布了一个事件
        publisher.publishEvent(orderCancelBO);
        System.out.println("");
    }
}
