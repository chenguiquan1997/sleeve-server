package com.quan.windsleeve.core.enums;

import java.util.stream.Stream;


public enum OrderStatus {

    UNPAID(1,"待支付"),
    PAID(2,"已支付"),
    DELIVERED(3,"已发货"),
    FINISHED(4,"已完成"),
    CANCLED(5,"已取消");

    private Integer code;
    private String message;

    OrderStatus(Integer code,String message) {
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

    public static OrderStatus toType(Integer code) {
        return Stream.of(OrderStatus.values())
                .filter(c -> c.code == code)
                .findAny().orElse(null);
    }
}
