package com.quan.windsleeve.exception.http;

public class OrderStatusException extends HttpException {

    public OrderStatusException(Integer code) {
        this.setCode(code);
        this.setHttpStatusCode(500);
    }
}
