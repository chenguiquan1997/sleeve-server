package com.quan.windsleeve;

import com.quan.windsleeve.OCPTest.reflect.HeroConfiguration;
import com.quan.windsleeve.OCPTest.reflect.Kill;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

//@ComponentScan
@EnableAutoLOLConfiguration
public class LOLApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                new SpringApplicationBuilder(LOLApplication.class)
                        .web(WebApplicationType.NONE).run(args);

        Kill kill = (Kill) context.getBean("Gailun");
        kill.r();
    }


}
