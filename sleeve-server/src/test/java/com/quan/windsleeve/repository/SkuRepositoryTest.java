package com.quan.windsleeve.repository;

import com.quan.windsleeve.model.Sku;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class SkuRepositoryTest {

    @Autowired
    private SkuRepository skuRepository;

    @Test
    void findAllByIdIn() {
        List<Long> skuIdList = new ArrayList<>();
        skuIdList.add(new Long(1));
        skuIdList.add(new Long(2));
        skuIdList.add(new Long(3));
        List<Sku> SkuList = skuRepository.findAllByIdIn(skuIdList);
        Assert.assertNotNull(skuIdList);
    }
}