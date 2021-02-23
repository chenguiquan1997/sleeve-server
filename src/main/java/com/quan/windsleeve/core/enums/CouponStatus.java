package com.quan.windsleeve.core.enums;

import java.util.stream.Stream;

public enum CouponStatus {

    AVAILABLE(1, "已领取，未使用"),
    USED(2,"已领取，已使用"),
    EXPIRED(3,"已过期");

    private Integer code;
    private String message;

    CouponStatus(Integer code, String message) {
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

    /**
     * 获取枚举中，指定 code 对应的优惠券状态
     * @param code
     * @return
     */
    public static CouponStatus toType(Integer code) {
        return Stream.of(CouponStatus.values())
                .filter(c -> c.code == code)
                .findAny()
                .orElse(null);
    }
}
