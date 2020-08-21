package com.quan.windsleeve.exception.http;

public class TypeNotMatchException extends HttpException{

    public TypeNotMatchException(Integer code) {
        this.setHttpStatusCode(500);
        this.setCode(code);
    }
}
