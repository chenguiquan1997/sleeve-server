package com.quan.windsleeve.exception.http;

public class NotFoundException extends HttpException {

    public NotFoundException(Integer code) {
        this.setHttpStatusCode(404);
        this.setCode(code);
    }

}
