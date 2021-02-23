package com.quan.windsleeve.exception.http;

public class SourceAlreadyExistException extends HttpException {

    public SourceAlreadyExistException(Integer code) {
        this.setHttpStatusCode(500);
        this.setCode(code);
    }
}
