package com.quan.windsleeve.dto;

import com.quan.windsleeve.validator.TokenPassword;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class TokenGetDTO {
    @NotBlank(message = "账号不能为空")
    private String account;
    @TokenPassword
    private String password;

    private String Type;
}
