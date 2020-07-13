package com.quan.windsleeve.util;

import com.quan.windsleeve.bo.PageCounter;

public class CommonUtils {

    public static PageCounter PageCounterConvert(Integer start, Integer count) {
        Integer pageNum = start / count;
        PageCounter pageCounter = new PageCounter();
        pageCounter.setPageNum(pageNum);
        pageCounter.setPageSize(count);
        return pageCounter;
    }
}
