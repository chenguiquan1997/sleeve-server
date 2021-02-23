package com.quan.windsleeve.bo;

import com.quan.windsleeve.dto.SkuInfoDTO;
import com.quan.windsleeve.model.Sku;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 订单中的Sku BO类
 */
@Getter
@Setter
public class OrderSkuBO {

    private Long id;
    private Integer count;
    private Long categoryId;
    //如果当前商品有折扣价，那么显示折扣价。如果没有，则显示原价
    private BigDecimal actualPrice;

    public OrderSkuBO(SkuInfoDTO skuInfoDTO, Sku sku) {
        this.id = skuInfoDTO.getId();
        this.count = skuInfoDTO.getCount();
        this.categoryId = sku.getCategoryId();
        this.actualPrice = sku.getActualPrice();
    }
}
