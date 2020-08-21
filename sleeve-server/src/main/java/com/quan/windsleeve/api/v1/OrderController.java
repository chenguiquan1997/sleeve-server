package com.quan.windsleeve.api.v1;

import com.quan.windsleeve.core.annotation.ScopeLevel;
import com.quan.windsleeve.dto.OrderDTO;
import com.quan.windsleeve.exception.http.ParameterException;
import com.quan.windsleeve.logic.OrderChecker;
import com.quan.windsleeve.service.IOrderService;
import com.quan.windsleeve.util.LocalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @PostMapping("/create")
    @ScopeLevel
    @ResponseBody
    public Map<String,Object> createOrder(@RequestBody OrderDTO orderDTO) {
        Long userId = LocalUser.getUser().getId();
        //校验订单
        OrderChecker orderChecker = orderService.isOK(userId,orderDTO);
        //正式创建订单
        orderService.createOrder(userId,orderChecker,orderDTO);
        Map<String,Object> result = new HashMap<>();
        result.put("result","下单成功!");
        return  result;
    }
}
