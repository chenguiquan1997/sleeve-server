package com.quan.windsleeve.exception.http;

/**
 * @Author Guiquan Chen
 * @Date 2021/6/30 14:22
 * @Version 1.0
 * 使用Luence查询时，抛出的异常
 */
public class LuenceQueryException extends HttpException{

    public LuenceQueryException(Integer code) {
        this.setCode(code);
        this.setHttpStatusCode(503);
    }
}
