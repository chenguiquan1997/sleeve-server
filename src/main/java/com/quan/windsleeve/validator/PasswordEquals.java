package com.quan.windsleeve.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
//当前注解是通过@Constraint注解中标注的类，关联在一起的
@Constraint(validatedBy = PasswordValidator.class)
//保留，表示当前的注解需要保留的程序运行生命周期的什么时候
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PasswordEquals {
    //注意：在注解中声明的方法，它的返回值类型只能是基本数据类型
    int min() default 5;
    int max() default 20;
    String message() default "PassWord is not Equals";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};


}
