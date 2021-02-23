package com.quan.windsleeve.core.money;

import java.math.BigDecimal;

public interface IMoneyDiscount {

    public BigDecimal discount(BigDecimal originMoney, BigDecimal rate);
}
