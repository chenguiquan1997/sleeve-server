package com.quan.windsleeve.exception.http;

public class ParameterException extends HttpException {

    public ParameterException(Integer code) {
        this.setHttpStatusCode(500);
        this.setCode(code);
    }
}
