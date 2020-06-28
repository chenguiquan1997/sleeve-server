package com.quan.windsleeve.OCPTest.reflect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HeroConfiguration {

    @Bean
    public Kill Gailun() {
        return new Gailun();
    }

}
