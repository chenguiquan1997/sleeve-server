package com.quan.windsleeve.manager.rocketMq;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 当前线程池用于后台处理 发送到rocketmq 失败的消息，采用静态内部类
 */
public class HandleFailMqThreadPool {

    private ExecutorService mqThreadPool = Executors.newSingleThreadExecutor();

    private static class SingleTon {
        private static HandleFailMqThreadPool instance;

        static {
            instance = new HandleFailMqThreadPool();
        }

        public static HandleFailMqThreadPool getInstance() {
            return instance;
        }
    }

    //从静态内部类中获取线程池实例
    private static HandleFailMqThreadPool initInstance() {
        return SingleTon.getInstance();
    }

    //对外开放的获取线程池实例的方法
    public static HandleFailMqThreadPool getInstance() {
        return initInstance();
    }
}
