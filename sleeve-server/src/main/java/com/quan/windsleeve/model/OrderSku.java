package com.quan.windsleeve.model;

import com.quan.windsleeve.dto.SkuInfoDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderSku {

    //sku id
    private Long id;
    private Long spuId;
    //sku 单价 * sku count
    private BigDecimal finalPrice;
    //sku 单价
    private BigDecimal singlePrice;
    //规格值列表
    private List<Object> specValues;
    //购买数量
    private Integer count;
    private String img;
    private String title;

    public OrderSku() {}

    public OrderSku(Sku sku, SkuInfoDTO skuInfoDTO) {

        this.id = sku.getId();
        this.spuId = sku.getSpuId();
        this.singlePrice = sku.getActualPrice();
        this.finalPrice = sku.getActualPrice().multiply(new BigDecimal(skuInfoDTO.getCount()));
        this.count = skuInfoDTO.getCount();
        this.img = sku.getImg();
        this.title = sku.getTitle();
        this.specValues = sku.getSpecValue();
    }


}
