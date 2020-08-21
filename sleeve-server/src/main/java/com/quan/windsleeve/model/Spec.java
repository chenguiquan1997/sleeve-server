package com.quan.windsleeve.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 专门描述一个sku的规格名和规格值的类
 */
@Setter
@Getter
public class Spec {

    @JsonProperty("key_id")
    private Integer keyId;
    private String key;

    @JsonProperty("value_id")
    private Integer valueId;
    private String value;
}
