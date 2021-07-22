package com.quan.windsleeve.core.annotation;

import com.quan.windsleeve.validator.TokenPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented

//保留，表示当前的注解需要保留的程序运行生命周期的什么时候
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD,ElementType.METHOD})
public @interface ScopeLevel {
    String message() default "scopeLevel 的默认值为4";
    int value() default 4;

    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
