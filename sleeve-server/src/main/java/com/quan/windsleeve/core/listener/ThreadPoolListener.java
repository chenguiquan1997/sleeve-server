package com.quan.windsleeve.core.listener;

import com.quan.windsleeve.manager.rocketMq.HandleFailMqThreadPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ThreadPoolListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        HandleFailMqThreadPool.getInstance();
        System.out.println("====================监听器已经初始化=============");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
