package com.quan.windsleeve.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SkuInfoDTO {

    @NotNull
    private Long id;

    @Min(value = 1,message = "购买商品的sku数量不能小于1")
    private Integer count;
}
