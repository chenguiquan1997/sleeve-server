package com.quan.windsleeve.validator;


import com.quan.windsleeve.dto.PersonDTO;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @PasswordEquals注解的关联类，当前类必须要实现 ConstraintValidator接口，并且重写
 * isValid()；
 * ConstraintValidator接口后面需要填写两个参数，第一个为当前类关联的注解，第二个参数为自定义
 * 注解修饰的目标类型。如果当前注解
 */
public class PasswordValidator implements ConstraintValidator<PasswordEquals,PersonDTO> {

    private int minPw;
    private int maxPw;

    @Override
    public void initialize(PasswordEquals constraintAnnotation) {
        this.minPw = constraintAnnotation.min();
        this.maxPw = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(PersonDTO personDTO, ConstraintValidatorContext constraintValidatorContext) {
        String p1 = personDTO.getPassword1();
        String p2 = personDTO.getPassword2();
        if(p1.equals(p2)) {
            return true;
        }else {
            return false;
        }
    }
}
