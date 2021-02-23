package com.quan.windsleeve.core.task;

import com.quan.windsleeve.manager.rocketMq.FailMsg;
import com.quan.windsleeve.manager.rocketMq.RocketMqRepository;
import com.quan.windsleeve.manager.rocketMq.ScheduleProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 当前类用于将发送到rocketmq失败的消息，重新发送
 */
@Component
public class HandleMqFailMsgSchedule {

    @Autowired
    private RocketMqRepository rocketMqRepository;

    @Autowired
    private ScheduleProducer scheduleProducer;

    /**
     * fixedRate 表示任务执行之间的时间间隔，具体是指两次任务的开始时间间隔，即第二次任务开始时，第一次任务可能还没结束。
     * fixedDelay 表示任务执行之间的时间间隔，具体是指本次任务结束到下次任务开始之间的时间间隔。
     * initialDelay 表示首次任务启动的延迟时间。
     * 都是以毫秒为时间单位
     */
    @Scheduled(fixedDelay = 600000, initialDelay = 600000)
    public void handleMqFailMsg() {
        List<FailMsg> failMsgList = rocketMqRepository.findAll();
        if(failMsgList == null || failMsgList.size() == 0) {
            System.out.println("无发送失败消息，当前task已退出！");
            return;
        }
        failMsgList.forEach(failMsg -> {
            String status = scheduleProducer.sendMsg(failMsg.getKeyId(),failMsg.getMessage());
            if(status.equals("SEND_OK") || status.equals("FLUSH_DISK_TIMEOUT") || status.equals("FLUSH_SLAVE_TIMEOUT") || status.equals("SLAVE_NOT_AVAILABLE")) {
                rocketMqRepository.updateSendSuccessMsg(new Date(),failMsg.getKeyId());
            }
        });
    }
}
