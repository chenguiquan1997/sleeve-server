package com.quan.windsleeve.service.impl;

import com.quan.windsleeve.core.enums.OrderStatus;
import com.quan.windsleeve.logic.OrderChecker;
import com.quan.windsleeve.model.OrderSku;
import com.quan.windsleeve.repository.SkuRepository;
import com.quan.windsleeve.repository.UserCouponRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class OrderServiceImplTest {

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Autowired
    private SkuRepository skuRepository;

    @Autowired
    private OrderServiceImpl orderService;

    @Test
    @Transactional
    void cancelAfterVerificationCoupon() {
        Long userId = new Long(4);
        Long couponId = new Long(1);
        Long orderId = new Long(10);
        int result = userCouponRepository
                .cancelAfterVerificationCoupon(userId,couponId,orderId);
        System.out.println(result);
        Assert.assertEquals(1,result);
    }

    @Test
    @Transactional
    void reduceStock() {
        List<OrderSku> orderSkuList = new ArrayList<>();

        OrderSku orderSku = new OrderSku();
        orderSku.setId(new Long(1));
        orderSku.setCount(new Integer(3));

        OrderSku orderSku1 = new OrderSku();
        orderSku1.setId(new Long(2));
        orderSku1.setCount(new Integer(2));

        orderSkuList.add(orderSku);
        orderSkuList.add(orderSku1);

        OrderChecker orderChecker = new OrderChecker(null);
        orderChecker.setOrderSkuList(orderSkuList);

        orderService.reduceStock(orderChecker);

    }

    @Test
    void returnStore() {
        String key = "123:456:-1";
        orderService.returnOfInventory(key);
        System.out.println("测试输出");
    }


}