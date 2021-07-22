package com.quan.windsleeve.dto;

import com.quan.windsleeve.validator.TokenPassword;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class TokenGetDTO {
    /**
     * 小程序中登录凭证：code码 ---> 使用wx.login()方法获取的
     */
    @NotBlank(message = "账号不能为空")
    private String account;
    @TokenPassword
    private String password;

    private String Type;
}
