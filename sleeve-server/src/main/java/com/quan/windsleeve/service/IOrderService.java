package com.quan.windsleeve.service;

import com.quan.windsleeve.dto.OrderDTO;
import com.quan.windsleeve.logic.OrderChecker;

public interface IOrderService {
    /**
     * 判断当前订单是否可以通过所有的订单校验
     * @param userId
     * @param orderDTO
     */
    public OrderChecker isOK(Long userId, OrderDTO orderDTO);

    public void createOrder(Long userId,OrderChecker orderChecker,OrderDTO orderDTO);
}
