package com.quan.windsleeve.core.hack;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * RequestMappingHandlerMapping是Spring boot中用来处理url映射的类，如果我们想要实现自定义url,
 * 那么就需要重写该方法
 * 问题：当前类即使extends了Spring boot中的类，但也仅仅是一个普通的类，那么框架是如何发现并且
 *       使用它的呢？需要有一个配置类来调用它
 */
public class AutoPrefixUrlMapping extends RequestMappingHandlerMapping {

    @Value("${sleeve.api-package}")
    private String apiRootPath;

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo requestMappingInfo = super.getMappingForMethod(method, handlerType);
        if(requestMappingInfo != null) {
            String path = getApiPackageName(handlerType);
           RequestMappingInfo newRequestMappingInfo =
                   RequestMappingInfo.paths(path).build().combine(requestMappingInfo);
           // System.out.println(newRequestMappingInfo.getPatternsCondition());
           return newRequestMappingInfo;
        }
        return requestMappingInfo;
    }

    /**
     * 获取Controller所在的包路径
     * @param handlerType
     * @return
     */
    public String getApiPackageName( Class<?> handlerType) {
       String packageName = handlerType.getPackage().getName();
       String path = packageName.replaceAll(apiRootPath,"").replace(".","/");
       return path;
    }
}
