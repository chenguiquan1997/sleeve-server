package com.quan.windsleeve.model;

import com.alibaba.fastjson.JSONObject;
import com.quan.windsleeve.util.ListAndJson;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Sku extends BaseEntity{
    @Id
    private Long id;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private Boolean online;
    private String img;
    private String title;
    private Long spuId;
    @Convert(converter = ListAndJson.class)
    private List<Spec> specs;
    private String code;
    private Integer stock;


}
