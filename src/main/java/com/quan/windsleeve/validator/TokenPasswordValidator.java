package com.quan.windsleeve.validator;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 泛型中的第一个参数为：需要验证哪一个注解
 * 第二个参数为：需要验证的目标对象的类型，因为这里需要验证Password，所以需要填写String
 */
public class TokenPasswordValidator implements ConstraintValidator<TokenPassword,String> {

   private Integer min;
   private Integer max;

    @Override
    public void initialize(TokenPassword tokenPassword) {
        this.max = tokenPassword.max();
        this.min = tokenPassword.min();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        /**
         * 如果用户输入的密码为空，那么我们默认是：小程序的静默登录，不需要输入密码
         */
        if(StringUtils.isEmpty(password)) {
            return true;
        }else {
            return password.length() > this.min && password.length() < this.max;
        }
    }
}
