package com.quan.windsleeve.core.money;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 该类用户两个BigDecimal相乘，得到的value，进行去精度计算
 */
public class HalfEvenRound implements IMoneyDiscount {

    @Override
    public BigDecimal discount(BigDecimal originMoney, BigDecimal rate) {
        //根据订单计算的真实价格，但是没有确定精度
        BigDecimal actualMoney = originMoney.multiply(rate);
        //添加精度后，得到的最终订单金额，使用的是 银行家算法 舍入模型
        BigDecimal finalMoney = actualMoney.setScale(2, RoundingMode.HALF_EVEN);
        return finalMoney;
    }
}
