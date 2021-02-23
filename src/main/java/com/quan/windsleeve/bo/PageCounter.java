package com.quan.windsleeve.bo;

import lombok.Getter;
import lombok.Setter;


public class PageCounter {

    //代表第几页
    private Integer pageNum;
    //代表每页多少条
    private Integer pageSize;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
}
