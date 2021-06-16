package com.quan.windsleeve.exception.http;

/**
 * @Author Guiquan Chen
 * @Date 2021/6/16 15:21
 * @Version 1.0
 * 系统获取不到用户openId时，抛出的异常
 */
public class NoOpenIdException extends HttpException {

    public NoOpenIdException(Integer code) {
        this.setCode(code);
        this.setHttpStatusCode(401);
    }
}
