package com.quan.windsleeve.exception.http;

public class StockException extends HttpException {

    public StockException(Integer code) {
        this.setHttpStatusCode(500);
        this.setCode(code);
    }
}
