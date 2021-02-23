package com.quan.windsleeve.exception.http;

public class PaymentException extends HttpException {

    public PaymentException(Integer code) {
        this.setCode(code);
        this.setHttpStatusCode(500);
    }
}
