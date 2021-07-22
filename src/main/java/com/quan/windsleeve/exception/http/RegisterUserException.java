package com.quan.windsleeve.exception.http;

/**
 * @Author Guiquan Chen
 * @Date 2021/6/1 11:09
 * @Version 1.0
 */
public class RegisterUserException extends HttpException {

    public RegisterUserException(Integer code) {
        this.setCode(code);
        this.setHttpStatusCode(500);
    }
}
