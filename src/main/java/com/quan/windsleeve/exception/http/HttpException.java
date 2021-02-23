package com.quan.windsleeve.exception.http;

/**
 * 已知的Http异常
 */
public class HttpException extends RuntimeException {


    private Integer code;

    private Integer httpStatusCode;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public HttpException(String message) {
        super(message);
    }

    public HttpException() {}
}
