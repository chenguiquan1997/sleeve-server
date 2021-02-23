package com.quan.windsleeve.service;

import com.quan.windsleeve.model.Sku;

import java.util.List;

public interface ISkuService {

    List<Sku> findSkuListBySkuId(List<Long> skuList);
}
