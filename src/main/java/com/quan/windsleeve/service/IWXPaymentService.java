package com.quan.windsleeve.service;

import com.quan.windsleeve.model.Orders;

import java.util.Map;

public interface IWXPaymentService {

    Orders theOrderWhetherExpireOrAlreadyPay(Long id, Long userId);

    String unifiedorder(Orders order);

    public Map<String,String> paymentRequest(String prepayId);
}
