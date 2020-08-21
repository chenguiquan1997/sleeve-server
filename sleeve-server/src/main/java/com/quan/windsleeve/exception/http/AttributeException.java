package com.quan.windsleeve.exception.http;

public class AttributeException extends HttpException {

    public AttributeException(Integer code) {
        this.setHttpStatusCode(500);
        this.setCode(code);
    }
}
