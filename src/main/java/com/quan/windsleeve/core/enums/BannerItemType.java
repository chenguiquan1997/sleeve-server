package com.quan.windsleeve.core.enums;

import java.util.stream.Stream;

/**
 * @Author Guiquan Chen
 * @Date 2021/7/22 14:59
 * @Version 1.0
 */
public enum BannerItemType {

    SPU(1,"商品"),
    THEME(2,"主题"),
    SPU_LIST(3,"商品列表");

    private Integer code;
    private String message;

    BannerItemType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static BannerItemType toType(Integer code) {
        return Stream.of(BannerItemType.values())
                .filter(c -> c.code == code)
                .findAny()
                .orElse(null);
    }
}
