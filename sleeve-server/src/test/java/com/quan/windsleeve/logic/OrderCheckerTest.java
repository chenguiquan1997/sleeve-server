package com.quan.windsleeve.logic;

import com.quan.windsleeve.bo.OrderSkuBO;
import com.quan.windsleeve.dto.OrderAddressDTO;
import com.quan.windsleeve.dto.OrderDTO;
import com.quan.windsleeve.dto.SkuInfoDTO;
import com.quan.windsleeve.model.OrderSku;
import com.quan.windsleeve.model.Sku;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class OrderCheckerTest {




    public static OrderDTO createTestData() {
        OrderDTO orderDTO = new OrderDTO();

        OrderAddressDTO addressDTO = new OrderAddressDTO();
        addressDTO.setCity("广州市");
        addressDTO.setCounty("海珠区");
        addressDTO.setDetail("397号");
        addressDTO.setMobile("020-811");
        addressDTO.setNationalCode("510000");
        addressDTO.setProvince("广东省");
        addressDTO.setUserName("张三");
        addressDTO.setPostalCode("510000");

        List<SkuInfoDTO> skuInfoDTOList = new ArrayList<>();

        SkuInfoDTO skuInfoDTO = new SkuInfoDTO();
        skuInfoDTO.setId(new Long(1));
        skuInfoDTO.setCount(2);

        SkuInfoDTO skuInfoDTO1 = new SkuInfoDTO();
        skuInfoDTO1.setId(new Long(2));
        skuInfoDTO1.setCount(3);

        skuInfoDTOList.add(skuInfoDTO);
        skuInfoDTOList.add(skuInfoDTO1);

        orderDTO.setCouponId(new Long(1));
        orderDTO.setAddress(addressDTO);
        orderDTO.setSkuInfoList(skuInfoDTOList);
        orderDTO.setFinalTotalPrice(new BigDecimal("353.51"));
        orderDTO.setTotalPrice(new BigDecimal("353.52"));

        return orderDTO;
    }

    public static List<OrderSkuBO> createOrderSkuBOData() {
        OrderDTO orderDTO = createTestData();
        List<Sku> skuList = createServerSkuData();

        List<OrderSkuBO> orderSkuBOList = new ArrayList<>();
        List<SkuInfoDTO> skuInfoDTOList = orderDTO.getSkuInfoList();
        //根据skuList 与 List<SkuInfoDTO> 来生成 orderSkuList
        List<OrderSku> orderSkuList = new ArrayList<>();

        skuList.forEach(sku -> {
            skuInfoDTOList.forEach(skuInfoDTO -> {
                if(sku.getId().equals(skuInfoDTO.getId())) {
                    OrderSkuBO orderSkuBO = new OrderSkuBO(skuInfoDTO,sku);
                    OrderSku orderSku = new OrderSku(sku,skuInfoDTO);
                    orderSkuBOList.add(orderSkuBO);
                    orderSkuList.add(orderSku);
                }
            });
        });

     return orderSkuBOList;
    }

    public static List<Sku> createServerSkuData() {
        List<Sku> skuList = new ArrayList<>();

        Sku sku = new Sku();
        sku.setId(new Long(1));
        sku.setPrice(new BigDecimal("77.76"));
        sku.setDiscountPrice(null);
        sku.setCategoryId(new Long(14));

        Sku sku1 = new Sku();
        sku1.setId(new Long(2));
        sku1.setCategoryId(new Long(14));
        sku1.setPrice(new BigDecimal("66.00"));
        sku1.setDiscountPrice(new BigDecimal("59.00"));

        skuList.add(sku);
        skuList.add(sku1);

        return skuList;
    }

    @Test
    void orderIsOK() {
        OrderDTO orderDTO = createTestData();
        List<OrderSkuBO> orderSkuBOList = createOrderSkuBOData();
        OrderChecker orderChecker = new OrderChecker(new CouponChecker());
        orderChecker.setOrderDTO(orderDTO);
        orderChecker.setOrderSkuBOList(orderSkuBOList);
        BigDecimal serverTotalPrice = orderChecker.compareTotalPrice(orderDTO);
        System.out.println(serverTotalPrice);
    }

    @Test
    void buySkuCountIsOutOfCurrentSkuStock() {
    }

    @Test
    void skuIsOutOfStock() {
    }

    @Test
    void skuIsOnSale() {
    }

    @Test
    void compareTotalPrice() {
    }
}