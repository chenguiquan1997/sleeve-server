package com.quan.windsleeve.exception.http;

public class NoAuthorizationException extends HttpException {

    public NoAuthorizationException(Integer code) {
        this.setCode(code);
        this.setHttpStatusCode(401);
    }
}
