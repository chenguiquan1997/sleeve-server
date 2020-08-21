package com.quan.windsleeve.exception.http;

public class ResultValidateException extends HttpException {

    public ResultValidateException(Integer code) {
        this.setHttpStatusCode(500);
        this.setCode(code);
    }

    public ResultValidateException(Integer code,String message) {
        super(message);
        this.setHttpStatusCode(500);
        this.setCode(code);

    }
}
