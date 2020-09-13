package com.quan.windsleeve.service.impl;

import com.quan.windsleeve.service.IWXPaymentNotifyService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

@Service
public class WXPaymentNotifyServiceImpl implements IWXPaymentNotifyService {

    public void handleWXNotify(HttpServletRequest request) {
        InputStream inputStream;
        try {
            inputStream = request.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
