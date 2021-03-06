package com.quan.windsleeve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class WindSleeveApplication {

    public static void main(String[] args) {
        /**
         * run();会返回一个ConfigurableApplicationContext对象
         */
        SpringApplication.run(WindSleeveApplication.class, args);
    }

}
