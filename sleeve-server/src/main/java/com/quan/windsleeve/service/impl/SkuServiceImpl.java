package com.quan.windsleeve.service.impl;

import com.quan.windsleeve.model.Sku;
import com.quan.windsleeve.repository.SkuRepository;
import com.quan.windsleeve.service.ISkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkuServiceImpl implements ISkuService {

    @Autowired
    private SkuRepository skuRepository;


    @Override
    public List<Sku> findSkuListBySkuId(List<Long> skuList) {
        return skuRepository.findAllByIdIn(skuList);
    }
}
