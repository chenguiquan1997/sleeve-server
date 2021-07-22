package com.quan.windsleeve.exception.http;

/**
 * @Author Guiquan Chen
 * @Date 2021/6/16 15:18
 * @Version 1.0
 */
public class GetTokenException extends HttpException{

    public GetTokenException(Integer code) {
        this.setCode(code);
        this.setHttpStatusCode(401);
    }
}
