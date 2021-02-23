package com.quan.windsleeve.exception.http.response;

import com.quan.windsleeve.exception.http.HttpException;

public class CreateSuccess extends HttpException {

    public CreateSuccess(int code) {
        this.setCode(code);
        //201表示：请求已经被实现，而且有一个新的资源已经依据请求的需要而建立
        this.setHttpStatusCode(201);
    }
}
