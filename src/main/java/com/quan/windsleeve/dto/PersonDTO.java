package com.quan.windsleeve.dto;

import com.quan.windsleeve.validator.PasswordEquals;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 在lombok中，经常用到的有三个注解，@Getter,@Setter,@Data,
 * @Data注解与其它两个注解的区别是：不仅仅为属性生成了Get和Set方法，还生成了例如
 * equals(),hashcde(),toString()，所以使用哪一个注解，需要根据业务来定
 */
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor()
@Builder
@PasswordEquals
public class PersonDTO {

    @NotNull(message = "用户名不能为空")
    @Length(min = 5,max = 12)
    private String name;

    @Range(min=1,max = 100)
    private Integer age;

    private String password1;

    private String password2;
    /**
     * @Valid 与 @Validated 注解的区别：
     * @Valid注解可以用于 方法、构造函数、方法参数和成员属性（字段）上
     * @Validated 可以用于类、方法和方法参数上。
     * 如果需要进行嵌套验证，那么需要在被嵌套的那个字段上，标注@Valid注解
     */
    @Valid
    private SchoolDTO schoolDTO;

//    public PersonDTO(@NonNull String name, Integer age) {
//        this.name = name;
//        this.age = age;
//    }
//
//    public PersonDTO() {}
}
