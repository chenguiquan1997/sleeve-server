package com.quan.windsleeve.exception.http;

public class ObjectConvertException extends HttpException {

    public ObjectConvertException(Integer code) {
        this.setCode(code);
        this.setHttpStatusCode(500);
    }
}
