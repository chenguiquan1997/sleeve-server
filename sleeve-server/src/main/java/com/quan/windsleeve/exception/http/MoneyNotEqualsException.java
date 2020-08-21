package com.quan.windsleeve.exception.http;

public class MoneyNotEqualsException extends HttpException {

    public MoneyNotEqualsException(Integer code) {
        this.setHttpStatusCode(500);
        this.setCode(code);
    }
}
