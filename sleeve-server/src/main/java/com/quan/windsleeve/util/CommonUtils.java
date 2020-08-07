package com.quan.windsleeve.util;

import com.quan.windsleeve.bo.PageCounter;
import com.quan.windsleeve.exception.http.ParamNullException;

import java.util.Date;

public class CommonUtils {

    /**
     * 进行分页查询时，pageSize, pageCount 的转换方法
     * @param start
     * @param count
     * @return
     */
    public static PageCounter PageCounterConvert(Integer start, Integer count) {
        Integer pageNum = start / count;
        PageCounter pageCounter = new PageCounter();
        pageCounter.setPageNum(pageNum);
        pageCounter.setPageSize(count);
        return pageCounter;
    }

    public static Boolean judgeActivityIsValid(Date startTime, Date endTime, Date currTime) {
        if(startTime == null || endTime == null || currTime == null) {
            throw new ParamNullException(70001);
        }
        if(currTime.getTime() >= startTime.getTime() && currTime.getTime() <= endTime.getTime()) {
            return true;
        }
        return false;
    }
}
