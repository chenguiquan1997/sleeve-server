package com.quan.windsleeve.api.v1;

import com.quan.windsleeve.bo.PageCounter;
import com.quan.windsleeve.core.annotation.ScopeLevel;
import com.quan.windsleeve.core.enums.OrderStatus;
import com.quan.windsleeve.dto.OrderDTO;
import com.quan.windsleeve.logic.OrderChecker;
import com.quan.windsleeve.model.Orders;
import com.quan.windsleeve.service.IOrderService;
import com.quan.windsleeve.util.CommonUtils;
import com.quan.windsleeve.util.LocalUser;
import com.quan.windsleeve.vo.OrderSimplifyVO;
import com.quan.windsleeve.vo.PagingMappering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
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
    public Map<String,Object> createOrder(@RequestBody @Validated OrderDTO orderDTO) {
        Long userId = LocalUser.getUser().getId();
        //统一BigDecimal格式
        orderDTO = CommonUtils.unifyBigdecimalFormat(orderDTO);
        //校验订单
        OrderChecker orderChecker = orderService.isOK(userId,orderDTO);
        //正式创建订单
        orderService.createOrder(userId,orderChecker,orderDTO);
        Map<String,Object> result = new HashMap<>();
        result.put("result","下单成功!");
        return  result;
    }

    /**
     * 获取当前用户的所有订单
     * @param start
     * @param count
     * @return
     */
    @GetMapping("/all")
    @ScopeLevel
    public PagingMappering getAllOrders(@RequestParam(defaultValue = "0") Integer start,
                             @RequestParam(defaultValue = "3") Integer count) {
        Long userId = LocalUser.getUser().getId();
        PageCounter pageCounter = CommonUtils.PageCounterConvert(start, count);
        Page<Orders> ordersPage = orderService.findAllOrders(userId,pageCounter);
        PagingMappering pagingMapper = new PagingMappering(ordersPage,OrderSimplifyVO.class);
        return pagingMapper;
    }

    /**
     * 获取当前用户所有待支付的订单集合
     */
    @GetMapping("/status/unpaid")
    @ScopeLevel
    public PagingMappering getWaitPayOrders(@RequestParam(defaultValue = "0") Integer start,
                                 @RequestParam(defaultValue = "3") Integer count) {
        Long userId = LocalUser.getUser().getId();
        PageCounter pageCounter = CommonUtils.PageCounterConvert(start,count);
        Page<Orders> orderPage = orderService.findWaitPayOrders(userId, OrderStatus.UNPAID.getCode(),
                pageCounter);
        PagingMappering pageMapper = new PagingMappering(orderPage, OrderSimplifyVO.class);
        return pageMapper;
    }

    /**
     * 根据相应状态获取当前用户订单
     * @param status
     * @param start
     * @param count
     * @return
     */
    @GetMapping("/by/status/{status}")
    @ScopeLevel
    @Validated
    public PagingMappering getOrderByStatus(@NotNull @PathVariable("status") Integer status,
                                            @RequestParam(defaultValue = "0") Integer start,
                                            @RequestParam(defaultValue = "3") Integer count) {
        Long userId = LocalUser.getUser().getId();
        PageCounter pageCounter = CommonUtils.PageCounterConvert(start, count);
        Page<Orders> ordersPage = orderService.findOrdersByStatus(userId,status,pageCounter);
        PagingMappering pagingMapper = new PagingMappering(ordersPage,OrderSimplifyVO.class);
        return pagingMapper;
    }

}