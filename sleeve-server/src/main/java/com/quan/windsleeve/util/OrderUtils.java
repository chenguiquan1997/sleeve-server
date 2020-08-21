package com.quan.windsleeve.util;

import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Component
public class OrderUtils {

    public static synchronized String getOrderNo() {
        StringBuffer orderNo = new StringBuffer();
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYMMddHHmmss");
        Date data = new Date();
        orderNo.append(dateFormat.format(data));
        Random random = new Random();
        int randomVal = random.nextInt(999999);
        orderNo.append(randomVal);
        return orderNo.toString();
    }

    public static Date createOrderExpireTime(Long LimitTime) {
        Date currTime = new Date();
        Long currTimeLong = currTime.getTime();
        Long limitLong = LimitTime * 60 * 1000;
        Long expireTimeLong = currTimeLong + limitLong;
        return new Date(expireTimeLong);
    }
}
