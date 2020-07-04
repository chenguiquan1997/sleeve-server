package com.quan.windsleeve.core.configuration;

import com.quan.windsleeve.core.hack.AutoPrefixUrlMapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 *WebMvcRegistrations它是一个用于配置SpringMVC的接口，那么需要考虑的是：在Boot中，每个注解都有特定的含义，
 * 那么当前类仅仅标注了@Component注解，那么IOC容器是如何确定当前类的功能的呢？
 * -->这就需要当前类实现特定的接口，实现了WebMvcRegistrations接口以后，Spring Boot就会在初始化容器时，
 * 将当前类载入
 * 在IOC容器初始化时，只要有@Controller注解标注的类，Spring Boot都会进行扫描
 */
@Component
public class AutoUrlPrefixConfiguration implements WebMvcRegistrations {

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new AutoPrefixUrlMapping();
    }
}
