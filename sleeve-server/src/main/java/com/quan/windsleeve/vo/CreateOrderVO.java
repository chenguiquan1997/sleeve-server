package com.quan.windsleeve.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Setter
@Getter
public class CreateOrderVO {

    public static HashMap<String,Object> placeOrderSuccess(Long orderId) {
        HashMap<String,Object> orderSuccess = new HashMap<>();
        orderSuccess.put("code","200");
        orderSuccess.put("message","下单成功!");
        if(orderId == null) {
            orderSuccess.put("orderId",-1L);
        }else {
            orderSuccess.put("orderId",orderId);
        }
        return orderSuccess;
    }
}
