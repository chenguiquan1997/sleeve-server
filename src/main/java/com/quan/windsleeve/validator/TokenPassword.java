package com.quan.windsleeve.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
//当前注解是通过@Constraint注解中标注的类，关联在一起的
@Constraint(validatedBy = TokenPasswordValidator.class)
//保留，表示当前的注解需要保留的程序运行生命周期的什么时候
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
public @interface TokenPassword {

    int min() default 10;
    int max() default 20;

    String message() default "密码输入不符合规则，最小值为10，最大值为20";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
