package com.quan.windsleeve.bo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCancelBO {

    private Long userId;
    private Long orderId;
    private Long couponId;

    public OrderCancelBO(String key) {
        String[] strings = key.split(":");
        this.userId = Long.valueOf(strings[0]);
        this.orderId = Long.valueOf(strings[1]);
        this.couponId = Long.valueOf(strings[2]);
    }
}
