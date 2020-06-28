package com.quan.windsleeve;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(HeroSelector.class)
public @interface EnableAutoLOLConfiguration {
}
