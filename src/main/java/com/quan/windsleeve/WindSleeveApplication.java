package com.quan.windsleeve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WindSleeveApplication {

    public static void main(String[] args) {
        /**
         * run();会返回一个ConfigurableApplicationContext对象
         */
        SpringApplication.run(WindSleeveApplication.class, args);
    }

}
