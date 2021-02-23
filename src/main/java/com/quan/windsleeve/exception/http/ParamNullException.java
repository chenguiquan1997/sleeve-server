package com.quan.windsleeve.exception.http;

public class ParamNullException extends HttpException {

    public ParamNullException(Integer code) {
        this.setCode(code);
        this.setHttpStatusCode(500);
    }
}
