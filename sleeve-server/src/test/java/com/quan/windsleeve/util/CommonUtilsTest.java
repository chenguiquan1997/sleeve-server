package com.quan.windsleeve.util;

import com.quan.windsleeve.dto.OrderDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CommonUtilsTest {

    @Test
    void yuanConvertFen() {
        String money = CommonUtils.yuanConvertFen(new BigDecimal("991"));

        System.out.println(money);
    }

    @Test
    void decimalTest() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setTotalPrice(new BigDecimal("2289.00"));
        orderDTO.setFinalTotalPrice(new BigDecimal("1987"));
        CommonUtils.unifyBigdecimalFormat(orderDTO);
    }


}