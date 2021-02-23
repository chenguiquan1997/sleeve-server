package com.quan.windsleeve.service.impl;

import com.quan.windsleeve.repository.OrderRepository;
import org.junit.Assert;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class WXPaymentServiceImplTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @Transactional
    void validateUpdatePrepayid() {
        int result = orderRepository
                .updatePrepayIdByOrderId(new Long(2),new Long(4),"123456xxx");
        Assert.assertEquals(0,result);
    }

}