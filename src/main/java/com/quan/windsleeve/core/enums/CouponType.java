package com.quan.windsleeve.core.enums;


import java.util.stream.Stream;

public enum CouponType {

    FULL_MINUS(1,"满减券"),
    DISCOUNT(2,"折扣券"),
    NO_AMBIT(3,"无门槛通用券");

    private Integer code;
    private String message;

    CouponType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    /**
     * 我们可以采用 Stream 这种形式来遍历枚举中所有的values
     * @param typeCode
     * @return
     */
    public static CouponType getType(Integer typeCode) {
       return Stream.of(CouponType.values())
                .filter(c -> c.code == typeCode)
                .findAny()
                .orElse(null);
    }
}
