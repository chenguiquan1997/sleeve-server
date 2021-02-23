package com.quan.windsleeve.exception.http;

public class OrderAlreadyExpireException extends HttpException {

    public OrderAlreadyExpireException(Integer code) {
        this.setCode(code);
        this.setHttpStatusCode(500);
    }
}
