package com.quan.windsleeve.core.configuration;

import com.quan.windsleeve.dto.OrderDTO;
import com.quan.windsleeve.logic.CouponChecker;
import com.quan.windsleeve.logic.OrderChecker;
import com.quan.windsleeve.model.Sku;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.List;

/**
 * 使用@Configuration + @Bean 的形式，也可以将创建的对象交给IOC容器去管理，
 * 这种形式会让创建对象更加灵活。使用@Scope注解，可以让对象以多例的形式创建
 */
@Configuration
public class OrderCheckerConfiguration {

    @Autowired
    private ObjectFactory<CouponChecker> couponCheckerFactory;


    @Bean
    @Scope("prototype")
    public OrderChecker getOrderCheckerObject() {
        return new OrderChecker(this.couponCheckerFactory.getObject());
    }
}
