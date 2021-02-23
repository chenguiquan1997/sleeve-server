package com.quan.windsleeve.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderDTO {

    @DecimalMin(value = "0.00",message = "订单金额不能小于0")
    @DecimalMax(value = "99999999.00",message = "订单金额不能大于99999999.00")
    private BigDecimal totalPrice;

    @DecimalMin(value = "0.00",message = "订单金额不能小于0")
    @DecimalMax(value = "99999999.00",message = "订单金额不能大于99999999.00")
    private BigDecimal finalTotalPrice;

    private Long couponId;

    @NotNull(message = "当前订单的sku不允许为空")
    private List<SkuInfoDTO> skuInfoList;

    @NotNull(message = "用户收货地址不能为空")
    private OrderAddressDTO address;
}
