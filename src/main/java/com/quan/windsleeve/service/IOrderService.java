package com.quan.windsleeve.service;

import com.quan.windsleeve.bo.PageCounter;
import com.quan.windsleeve.dto.OrderDTO;
import com.quan.windsleeve.logic.OrderChecker;
import com.quan.windsleeve.model.Orders;
import org.springframework.data.domain.Page;

public interface IOrderService {
    /**
     * 判断当前订单是否可以通过所有的订单校验
     * @param userId
     * @param orderDTO
     */
    public OrderChecker isOK(Long userId, OrderDTO orderDTO);

    public Long createOrder(Long userId,OrderChecker orderChecker,OrderDTO orderDTO);

    Page<Orders> findWaitPayOrders(Long userId, Integer status, PageCounter pageCounter);

    Page<Orders> findAllOrders(Long userId, PageCounter pageCounter);

    Page<Orders> findOrdersByStatus(Long userId, Integer status, PageCounter pageCounter);

    void returnOfInventory(String key);

    void updateOrderStatusToAlreadyPay(Long orderId, Long userId);


}
