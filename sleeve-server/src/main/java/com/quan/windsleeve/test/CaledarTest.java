package com.quan.windsleeve.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class CaledarTest {

    public static void main(String[] args) {

        StringBuffer orderNo = new StringBuffer();
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYMMddHHmmss");
        Date data = new Date();
        orderNo.append(dateFormat.format(data));
        Random random = new Random();
        int randomVal = random.nextInt(999999);
        orderNo.append(randomVal);
        System.out.println(orderNo);

    }
}
