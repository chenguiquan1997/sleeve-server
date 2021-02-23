package com.quan.windsleeve.util;

import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Component
public class OrderUtils {

    public static String getOrderNo1() {
        StringBuffer orderNo = new StringBuffer();
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYMMddHHmmss");
        Date data = new Date();
        orderNo.append(dateFormat.format(data));
        Random random = new Random();
        int randomVal = random.nextInt(999999);
        orderNo.append(randomVal);
        return orderNo.toString();
    }

    public static String getOrderNo() {
        StringBuffer orderNo = new StringBuffer();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYMMddHHmmss");
        String time = LocalDateTime.now().format(formatter);
        orderNo.append(time);
        Random random = new Random();
        int randomVal = getRandomCode(7);
        orderNo.append(randomVal);
        return orderNo.toString();
    }

    /**
     * 获取指定位数的随机数
     * @param size
     * @return
     */
    private static int getRandomCode(int size) {
        double n = Math.pow(10, (size - 1));
        int num;

        if (size > 1) {
            num = (int) (Math.random() * (9 * n) + n);
        } else {
            num = (int) (Math.random() * 10);
        }
        return num;
    }

    public static String makeOrderNo() {
        StringBuffer joiner = new StringBuffer();
        Calendar calendar = Calendar.getInstance();
        String mills = String.valueOf(calendar.getTimeInMillis());
        String micro = LocalDateTime.now().toString();
        String random = String.valueOf(Math.random()*1000).substring(0,2);
        joiner.append(Integer.toHexString(calendar.get(Calendar.MONTH)+1).toUpperCase())
                .append(calendar.get(Calendar.DAY_OF_MONTH))
                .append(mills.substring(mills.length()-5, mills.length()))
                .append(micro.substring(micro.length()-3, micro.length()))
                .append(random);
        return joiner.toString();

    }

    /**
     * 生成订单的过期事件
     * @param LimitTime 配置文件中的单位是秒
     * @return
     */
    public static Date createOrderExpireTime(Long LimitTime) {
        Date currTime = new Date();
        Long currTimeLong = currTime.getTime();
        Long limitLong = LimitTime * 1000;
        Long expireTimeLong = currTimeLong + limitLong;
        return new Date(expireTimeLong);
    }
}
