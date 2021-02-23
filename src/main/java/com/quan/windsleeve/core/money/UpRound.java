package com.quan.windsleeve.core.money;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class UpRound implements IMoneyDiscount {

    @Override
    public BigDecimal discount(BigDecimal originMoney, BigDecimal rate) {
        //根据订单计算的真实价格，但是没有确定精度
        BigDecimal actualMoney = originMoney.multiply(rate);
        //添加精度后，得到的最终订单金额，使用的是 向上进位 舍入模型
        BigDecimal finalMoney = actualMoney.setScale(2, RoundingMode.UP);
        return finalMoney;
    }
}
