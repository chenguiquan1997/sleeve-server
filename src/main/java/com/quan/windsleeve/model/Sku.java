package com.quan.windsleeve.model;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.quan.windsleeve.util.GenericAndJson;
import com.quan.windsleeve.util.ListAndJson;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Where(clause = "delete_time is null and online = 1")
public class Sku extends BaseEntity{
    @Id
    private Long id;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private Boolean online;
    private String img;
    private String title;
    private Long spuId;
    //@Convert(converter = ListAndJson.class)
    private String specs;
    private String code;
    private Integer stock;
    private Long categoryId;
    private Long rootCategoryId;

    public BigDecimal getActualPrice() {
        return discountPrice == null ? this.price : this.discountPrice;
    }

    /**
     * 当前方法用于获取spec集合中的value值
     * 使用jpa时，实体类中的方法，在序列化实体类时，会自动调用，如果我们不需要当前方法在序列化
     * 时被调用，那么需要添加@JsonIgnore注解
     * @return
     */
    @JsonIgnore
    public List<Object> getSpecValue() {
//        return this.getSpecs().stream().
//                map(Spec::getValue).collect(Collectors.toList());
        List<Object> specs = new ArrayList<>();
        this.getSpecs().forEach(spec -> {
            specs.add(spec.getValue());
        });
        return specs;
    }

    public List<Spec> getSpecs() {
        if (this.specs == null) {
            return Collections.emptyList();
        }
        return GenericAndJson.jsonToObject(this.specs, new TypeReference<List<Spec>>() {});
    }

    public void setSpecs(List<Spec> specs) {
        if(specs.isEmpty()){
            return;
        }
        this.specs = GenericAndJson.objectToJson(specs);
    }

}
