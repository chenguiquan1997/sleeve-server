package com.quan.windsleeve.service;

import com.quan.windsleeve.bo.OrderCancelBO;

public interface IOrderCancelService {

    void revertStock(OrderCancelBO orderCancelBO);
}
