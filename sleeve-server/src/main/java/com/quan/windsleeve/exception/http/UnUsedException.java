package com.quan.windsleeve.exception.http;

public class UnUsedException extends HttpException {

    public UnUsedException(Integer code) {
        this.setHttpStatusCode(500);
        this.setCode(code);
    }
}
