package com.quan.windsleeve.exception.http;

/**
 * @Author Guiquan Chen
 * @Date 2021/6/21 16:19
 * @Version 1.0
 * 创建Token令牌失败时，抛出的异常
 */
public class CreateJwtException extends HttpException{

    public CreateJwtException(Integer code) {
        this.setCode(code);
        this.setHttpStatusCode(401);
    }
}
