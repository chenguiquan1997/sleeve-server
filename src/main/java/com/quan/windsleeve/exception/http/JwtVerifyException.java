package com.quan.windsleeve.exception.http;

/**
 * @Author Guiquan Chen
 * @Date 2021/6/21 15:52
 * @Version 1.0
 * jwt令牌解析出现问题时，抛出的异常
 */
public class JwtVerifyException extends HttpException {

    public JwtVerifyException(Integer code) {
        this.setCode(code);
        this.setHttpStatusCode(401);
    }
}
