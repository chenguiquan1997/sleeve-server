package com.quan.windsleeve.exception.http;

public class CouponException extends HttpException {

    public CouponException(Integer code) {
        this.setHttpStatusCode(500);
        this.setCode(code);
    }
}
