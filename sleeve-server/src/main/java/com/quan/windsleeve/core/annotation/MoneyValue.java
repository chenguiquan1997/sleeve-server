package com.quan.windsleeve.core.annotation;


import com.quan.windsleeve.validator.TokenPasswordValidator;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Documented

@Constraint(validatedBy = TokenPasswordValidator.class)
//保留，表示当前的注解需要保留的程序运行生命周期的什么时候
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD,ElementType.METHOD})
public @interface MoneyValue {
}
