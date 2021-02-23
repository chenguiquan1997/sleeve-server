package com.quan.windsleeve.api.v1;

import com.quan.windsleeve.core.annotation.ScopeLevel;
import com.quan.windsleeve.model.Orders;
import com.quan.windsleeve.service.IOrderService;
import com.quan.windsleeve.service.IWXPaymentService;
import com.quan.windsleeve.util.LocalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Map;

@RestController
@RequestMapping("/payment")
@Validated
public class PaymentController {

    @Autowired
    private IWXPaymentService wxPaymentService;

    /**
     * 服务端只负责准备小程序支付需要的一系列参数，然后返回到小程序。具体的支付操作，
     * 是在小程序内部调用微信支付接口完成的
     * @param orderId
     * @return
     */
    @PostMapping("/pay/order/{id}")
    @ScopeLevel
    public Map<String,String> preOrder(@PathVariable("id") @NotNull @Positive Long orderId) {
        Long userId = LocalUser.getUser().getId();
        //校验当前订单是否已过期,校验当前订单是否已支付
        Orders order = wxPaymentService.theOrderWhetherExpireOrAlreadyPay(orderId,userId);
        //需要判断当前订单在之前是否生成过预支付订单，如果有生成，则不需要再次请求微信预支付
        String prepayId = order.getPrepayId();
        if(prepayId == null) {
            //请求微信生成预订单
            prepayId = wxPaymentService.unifiedorder(order);
        }
        //直接支付
        Map<String,String> miniPayParam = wxPaymentService.paymentRequest(prepayId);
        return miniPayParam;
    }

    /**
     * 小程序执行微信支付操作成功后，微信服务器调用的回调通知接口
     * @param request
     * @param response
     */
    @RequestMapping("/wx/notify")
    public void wxNotify(HttpServletRequest request,
                         HttpServletResponse response) {

    }
}
