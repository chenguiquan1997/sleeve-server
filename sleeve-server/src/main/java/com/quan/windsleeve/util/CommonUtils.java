package com.quan.windsleeve.util;

import com.quan.windsleeve.bo.PageCounter;
import com.quan.windsleeve.dto.OrderDTO;
import com.quan.windsleeve.exception.http.ParamNullException;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class CommonUtils {

    /**
     * 进行分页查询时，pageSize, pageCount 的转换方法
     * @param start
     * @param count
     * @return
     */
    public static PageCounter PageCounterConvert(Integer start, Integer count) {
        //pageNum 代表当前查询的是第几页数据
        Integer pageNum = start / count;
        PageCounter pageCounter = new PageCounter();
        pageCounter.setPageNum(pageNum);

        pageCounter.setPageSize(count);
        return pageCounter;
    }

    /**
     * 判断当前优惠活动是否有效，结束时间与当前时间做比较
     * @param startTime
     * @param endTime
     * @param currTime
     * @return
     */
    public static Boolean judgeActivityIsValid(Date startTime, Date endTime, Date currTime) {
        if(startTime == null || endTime == null || currTime == null) {
            throw new ParamNullException(70001);
        }
        if(currTime.getTime() >= startTime.getTime() && currTime.getTime() <= endTime.getTime()) {
            return true;
        }
        return false;
    }

    /**
     * "元" 转换成 "分" 的方法
     * @param money
     * @return
     */
    public static String yuanConvertFen(BigDecimal money) {
        if(money == null) return null;
        String moneyStr = money.movePointRight(2).toString();
        return moneyStr;
    }

    public static OrderDTO unifyBigdecimalFormat(OrderDTO orderDTO) {
        String totalPrice = orderDTO.getTotalPrice().toString();
        String finalTotalPrice = orderDTO.getFinalTotalPrice().toString();

        // 判断是否是整数或者是携带一位的小数
        Pattern pattern = Pattern.compile("^[+]?([0-9]+(.[0-9]{1})?)$");
        if (pattern.matcher(totalPrice).matches()) {
            orderDTO.setTotalPrice(new BigDecimal(totalPrice).setScale(2));
        }
        if (pattern.matcher(finalTotalPrice).matches()) {
            orderDTO.setFinalTotalPrice(new BigDecimal(finalTotalPrice).setScale(2));
        }
        return orderDTO;
    }

}
