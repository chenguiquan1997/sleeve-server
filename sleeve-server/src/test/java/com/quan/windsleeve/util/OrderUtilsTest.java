package com.quan.windsleeve.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class OrderUtilsTest {

    @Test
    void getOrderNo() {
        List<String> orderNoList = Collections.synchronizedList(new ArrayList<>());
        IntStream.range(0,10000).parallel().forEach(i->{
            String orderNo = OrderUtils.getOrderNo();
            orderNoList.add(orderNo);
        });
        List<String> distinctList = orderNoList.stream().distinct().collect(Collectors.toList());

        System.out.println("生成订单数量："+orderNoList.size());
        System.out.println("去重后的订单数量："+distinctList.size());
        Set<String> setList = new HashSet<>();
        for(String s : orderNoList) {
            if(setList.contains(s)) {
                System.out.println(s);
            }
            setList.add(s);
        }
    }

    @Test
    public void getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYMMddHHmmss");
        String time = LocalDateTime.now().format(formatter);
        System.out.println(time);
    }

    @Test
    public void getOrderNo1() {
//        int randomVal = (int) OrderUtils.getRandomCode(5);
//        System.out.println(randomVal);
    }
}