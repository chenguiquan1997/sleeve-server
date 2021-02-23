package com.quan.windsleeve.manager.rocketMq;

import com.quan.windsleeve.bo.OrderCancelBO;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 当前类可以通过 implements CommandLineRunner 接口，然后实现它的run(){}，
 * 当前这个run()只能执行一次，所以消费者的初始化也可以保证只执行一次，注册完MessageListener之后，
 * 监听器会一直不断地监听是否有消息进入到mq的broker中，如果有，就会立即进行消费
 */
//@Component
public class ScheduleConsumer implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(ScheduleConsumer.class);

    @Value("${spring.rocketmq.consumer.consumerGroup}")
    private String consumerGroup;

    @Value("${spring.rocketmq.nameSrvAddr}")
    private String nameSrvAddr;

    @Value("${spring.rocketmq.topic}")
    private String topic;

    private String tags = "orderTag";

    private static ApplicationEventPublisher publisher;

    @Autowired
    public void setPublisher(ApplicationEventPublisher publisher) {
        ScheduleConsumer.publisher = publisher;
    }




    public void messageListener() {
        //实例化一个消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(this.consumerGroup);
        //订阅topic以及tag来过滤消息
        try {
            consumer.subscribe(this.topic,this.tags);
            //设置消费者每次只消费一条数据
            consumer.setConsumeMessageBatchMaxSize(1);
        } catch (MQClientException e) {
            e.printStackTrace();
            log.error("MQClientException, 消费者端抛出的异常");
        }

        //消费者注册消息监听器
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for(Message msg : list) {
                    String body = new String(msg.getBody());
                    System.out.println("从消费者中获取的消息："+body);
                    System.out.println("标签："+msg.getTags());
                    System.out.println("topic: "+msg.getTopic());
                    //通过事件发布器来发布事件
                    ScheduleConsumer.publisher.publishEvent(new OrderCancelBO(body));

                }
                System.out.println("消费者推出循环......");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        //启动消费者
        try {
            consumer.start();
            log.info("延迟消息消费者启动成功");
        } catch (MQClientException e) {
            e.printStackTrace();
            log.info("延迟消息消费者启动失败");
        }
    }





    @Override
    public void run(String... args) throws Exception {
        messageListener();
    }



}
