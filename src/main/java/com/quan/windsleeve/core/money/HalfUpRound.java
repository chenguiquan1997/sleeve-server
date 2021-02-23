package com.quan.windsleeve.core.money;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class HalfUpRound implements IMoneyDiscount {

    @Override
    public BigDecimal discount(BigDecimal originMoney, BigDecimal rate) {
        //根据订单计算的真实价格，但是没有确定精度
        BigDecimal actualMoney = originMoney.multiply(rate);
        //添加精度后，得到的最终订单金额，使用的是 四舍五入 舍入模型
        BigDecimal finalMoney = actualMoney.setScale(2, RoundingMode.HALF_UP);
        return finalMoney;
    }
}
