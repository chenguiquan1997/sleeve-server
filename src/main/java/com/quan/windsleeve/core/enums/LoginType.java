package com.quan.windsleeve.core.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户登录方式枚举
 */

@Getter
public enum LoginType {
    WX_LOGIN(0,"微信登录"),
    EMAIL_LOGIN(1,"邮箱登录");

    private Integer key;
    private String description;

    LoginType(Integer key, String description) {
        this.key = key;
        this.description = description;
    }


}
