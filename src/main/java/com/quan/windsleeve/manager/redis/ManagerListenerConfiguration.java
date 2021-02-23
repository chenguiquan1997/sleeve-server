package com.quan.windsleeve.manager.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;

@Configuration
public class ManagerListenerConfiguration {

    @Value("${spring.redis.listen-pattern}")
    private String pattern;

    /**
     * RedisConnectionFactory,内部的config类会自动读取配置文件中的连接信息，然后根据连接信息
     * 实例化一个redisConnection连接对象
     * @param redisConnection
     * @return
     */
    @Bean
    public RedisMessageListenerContainer listenerContainer(RedisConnectionFactory redisConnection) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnection);
        //代表程序监听的主题是什么，例如：'__keyevent@1__:expired'
        Topic topic = new PatternTopic(pattern);
        TopicManagerListener listener = new TopicManagerListener();
        container.addMessageListener(listener,topic);
        return container;
    }
}
