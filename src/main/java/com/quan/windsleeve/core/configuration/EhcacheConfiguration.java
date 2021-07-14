package com.quan.windsleeve.core.configuration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * @Author Guiquan Chen
 * @Date 2021/7/5 14:58
 * @Version 1.0
 * Ehcache缓存的配置类
 */
@Configuration
@EnableCaching
public class EhcacheConfiguration {

    /**
     * @Description: 创建一个ehcache工厂对象，并且交给Spring管理
     * @return org.springframework.cache.ehcache.EhCacheManagerFactoryBean
     * @Author: Guiquan Chen
     * @Date: 2021/7/5
     */
    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactory() {
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("config/ehcache.xml"));
        ehCacheManagerFactoryBean.setShared(true);
        return ehCacheManagerFactoryBean;
    }

    @Bean(name = "ehcache1")
    public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean) {
        //EhCacheManagerFactoryBean的getObject()会返回一个EhCacheCacheManager
        System.out.println("ehcache对象创建成功");
        return new EhCacheCacheManager(bean.getObject());
    }

}
