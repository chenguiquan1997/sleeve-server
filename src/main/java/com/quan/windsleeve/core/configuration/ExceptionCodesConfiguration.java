package com.quan.windsleeve.core.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
//当前注解表示可以读取到的配置文件，让配置文件与当前类相对应
@PropertySource(value = "classpath:config/exception-code.properties",encoding = "utf-8")
//该注解表示当前类与配置文件的哪一个前缀相匹配，然后codes会自动匹配前缀后面的
@ConfigurationProperties(prefix = "sleeve")
//如果想让当前类与配置文件相对应，那么当前类必须被IOC容器所管理
@Component
public class ExceptionCodesConfiguration {

    /**
     * 当前的codes变量名，需要与配置文件中的sleeve.后面的变量名一致
     */
    private Map<Integer,String> codes = new HashMap<>();

    /**
     * get() 和 set() 都是在程序初始化时运行的，个人目前认为：get()是从配置文件中获取数据，
     * 然后set()是将数据设置到codes中
     * @return
     */
    public Map<Integer, String> getCodes() {
        return codes;
    }

    public void setCodes(Map<Integer, String> codes) {
        this.codes = codes;
    }

    /**
     * 根据code码，获取配置文件中的异常信息
     * @param code 异常信息码
     * @return 异常信息
     */
    public String getMessage(int code) {
        String message = codes.get(code);
        //System.out.println("查找到的message : "+message);
        return message;
    }
}
