package com.quan.windsleeve.manager.rocketMq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ScheduleProducer {

    private final Logger log = LoggerFactory.getLogger(ScheduleProducer.class);

    private DefaultMQProducer producer;

    @Value("${spring.rocketmq.nameSrvAddr}")
    private String nameSrvAddr;

    @Value("${spring.rocketmq.producer.producerGroup}")
    private String producerGroup;

    @Value("${spring.rocketmq.topic}")
    private String topic;

    private String tags = "orderTag";

    @Autowired
    private RocketMqRepository rocketMqRepository;

    public ScheduleProducer() {
        System.out.println();
    }

    /**
     *@PostConstruct 可以理解为在IOC容器启动时，主动运行。被该注解注释的方法，会在依赖注入完成之后自动调用
     * @PostConstruct 与 Constructor 与 @Autowired注解的执行顺序：Constructor > @AutoWired > @PostConstruct
     * 在Spring中，如果在A对象中需要注入B对象，那么前提时A对象必须初始化完成，然后B对象才能注入
     * @PostConstruct 注解的应用场景：
     */
    @PostConstruct
    private void initProducer() {
        if(this.producer == null) {
            //每个消息生产者必须要属于某一个ProducerGroup
            this.producer = new DefaultMQProducer(this.producerGroup);
            this.producer.setNamesrvAddr(this.nameSrvAddr);
        }
        try {
            this.producer.start();
            log.info("延迟消息生产者启动成功");
        } catch (MQClientException e) {
            e.printStackTrace();
            log.error("延迟消息生产者启动失败");
        }
    }

    public String sendMsg(String orderIdKey, String orderMsg) {
        //创建一条消息
        Message message = new Message(this.topic,this.tags,orderIdKey,orderMsg.getBytes());
        //设置延迟时间 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h 一共18个level
        message.setDelayTimeLevel(6);
        String status = null;
        FailMsg failMsg = FailMsg.builder().keyId(orderIdKey).message(orderMsg).build();
        try {
            SendResult result = this.producer.send(message);
            SendStatus sendStatus = result.getSendStatus();
            status = sendStatus.toString();
            System.out.println("消息的发送状态："+status);

        } catch (MQClientException e) {
            failMsg.setDescription("MQ客户端发送消息异常");
            rocketMqRepository.save(failMsg);
            e.printStackTrace();
            log.error("MQ客户端异常 orderId = "+orderIdKey);
        } catch (RemotingException e) {
            failMsg.setDescription("MQ远程传输异常");
            rocketMqRepository.save(failMsg);
            e.printStackTrace();
            log.error("MQ远程传输异常 orderId = "+orderIdKey);
        } catch (MQBrokerException e) {
            failMsg.setDescription("MQ Broker异常");
            rocketMqRepository.save(failMsg);
            e.printStackTrace();
            log.error("MQ Broker异常 orderId = "+orderIdKey);
        } catch (InterruptedException e) {
            failMsg.setDescription("MQ 中断异常");
            rocketMqRepository.save(failMsg);
            e.printStackTrace();
            log.error("MQ 中断异常 orderId = "+orderIdKey);
        }
        return status;
    }


}
