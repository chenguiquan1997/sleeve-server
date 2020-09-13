package com.quan.windsleeve.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.quan.windsleeve.model.OrderSku;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class OrderSimplifyVO {
    private Long id;
    private String orderNo;
    private Long userId;
    private Integer totalCount;
    private BigDecimal totalPrice;
    private BigDecimal finalTotalPrice;
    private String snapImg;
    private String snapTitle;
    private List<OrderSku> snapItems;
    private HashMap<String,Object> snapAddress;
    private String prepayId;
    private Integer status;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expireTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date placedTime;
}
