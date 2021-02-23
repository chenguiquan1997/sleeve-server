package com.quan.windsleeve.service.impl;

import com.github.wxpay.sdk.SleeveWXPayConfig;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.quan.windsleeve.core.enums.OrderStatus;
import com.quan.windsleeve.exception.http.NotFoundException;
import com.quan.windsleeve.exception.http.OrderAlreadyExpireException;
import com.quan.windsleeve.exception.http.OrderStatusException;
import com.quan.windsleeve.exception.http.PaymentException;
import com.quan.windsleeve.model.Orders;
import com.quan.windsleeve.repository.OrderRepository;
import com.quan.windsleeve.service.IWXPaymentService;
import com.quan.windsleeve.util.CommonUtils;
import com.quan.windsleeve.util.HttpRequestProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class WXPaymentServiceImpl implements IWXPaymentService {

    @Autowired
    private OrderRepository orderRepository;

    private static SleeveWXPayConfig config = new SleeveWXPayConfig();

    @Value("${missyou.pay.notify-host}")
    private String notifyHost;

    @Value("${missyou.pay.notify-url}")
    private String notifyUrl;

    /**
     * 根据orderId查询指定的订单
     * @param orderId
     * @return
     */
    @Override
    public Orders theOrderWhetherExpireOrAlreadyPay(Long orderId, Long userId) {
        Optional<Orders> optional = orderRepository.findOneByIdAndUserId(orderId,userId);
        Date currTime = new Date();
        Orders order = optional.orElseThrow(()-> new NotFoundException(50009));
        Date expireTime = order.getExpireTime();
        //判断了当前订单是否过期
        if(currTime.after(expireTime)) {
            throw new OrderAlreadyExpireException(50007);
        }
        //判断订单是否为待支付状态
        if(!order.getStatus().equals(OrderStatus.UNPAID)) {
            throw new OrderStatusException(50008);
        }
        return order;
    }

    /**
     * 向微信支付服务器发送请求，生成预支付交易单
     */
    @Override
    public String unifiedorder(Orders order) {
        WXPay wxPay = null;
        try {
           wxPay = new WXPay(WXPaymentServiceImpl.config);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PaymentException(50010);
        }
        Map<String,String> payParamMap = assembleWXPayParam(order);
        Map<String,String> preOrderResult = null;
        try {
            //调用当前方法，就可以实现订单的预支付操作
            preOrderResult = wxPay.unifiedOrder(payParamMap);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PaymentException(50010);
        }
        //校验生成预支付订单的结果
        validatePrePayResult(preOrderResult);

        String prepayId = preOrderResult.get("prepay_id");
        //需要更新数据库中，当前订单的prepayId字段
        orderRepository.updatePrepayIdByOrderId(order.getId(),order.getUserId(),prepayId);
        return prepayId;
    }

    @Override
    public Map<String,String> paymentRequest(String prepayId){
        Map<String,String> paySignMap = new HashMap<>();
        //appId
        String appId = config.getAppID();
        paySignMap.put("appId",appId);

        //获取当前系统时间，以秒为单位
        Long currTime = System.currentTimeMillis()/1000;
        String currTimeStr = currTime.toString();
        paySignMap.put("timeStamp",currTimeStr);

        //随机字符串，32位
        String str32 = WXPayUtil.generateNonceStr();
        paySignMap.put("nonceStr",str32);

        //signType 签名类型,在wxSdk中，沙箱环境是MD5,正式环境是HMAC-SHA256
        String signType = "HMAC-SHA256";
        paySignMap.put("signType",signType);

        paySignMap.put("package",prepayId);
        //签名
        String paySign;
        try {
           paySign = WXPayUtil.generateSignature(paySignMap,config.getKey(), WXPayConstants.SignType.HMACSHA256);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PaymentException(50011);
        }
        Map<String,String> miniPayParam = new HashMap<>();
        miniPayParam.putAll(paySignMap);
        miniPayParam.remove("appId");
        miniPayParam.put("paySign",paySign);
        return miniPayParam;
    }

    /**
     * 校验预支付的结果是否正确
     * @param preOrderResult
     * @return
     */
    private boolean validatePrePayResult(Map<String,String> preOrderResult) {
        if(!preOrderResult.get("return_code").equals("SUCCESS")) {
            throw new PaymentException(50010);
        }else {
            if(!preOrderResult.get("result_code").equals("SUCCESS")) {
                throw new PaymentException(50010);
            }
        }
        return true;
    }

    /**
     * 组装生成预支付订单的必要参数
     * appid、商户号、随机字符串、签名类型、签名这些参数，都不需要自己添加，jdk的unifiedOrder()中
     * 已经封装好
     * @param order 当前需要支付的订单信息
     * @return
     */
    private Map<String,String> assembleWXPayParam(Orders order) {

        Map<String,String> unifiedDataMap = new HashMap<>();
        //商品描述
        unifiedDataMap.put("total_fee","泉韵工作室");
        //订单id
        unifiedDataMap.put("out_trade_no",order.getOrderNo());
        //订单金额
        String money = CommonUtils.yuanConvertFen(order.getFinalTotalPrice());
        unifiedDataMap.put("total_fee",money);
        //设备ip-->用户使用的手机ip
        unifiedDataMap.put("spbill_create_ip", HttpRequestProxy.getRemoteRealIp());
        //通知地址
        unifiedDataMap.put("notify_url",notifyHost+notifyUrl);
        //交易类型 小程序 JSAPI
        unifiedDataMap.put("trade_type","JSAPI");

        return unifiedDataMap;
    }

}
