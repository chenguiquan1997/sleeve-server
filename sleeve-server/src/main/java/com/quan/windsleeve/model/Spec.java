package com.quan.windsleeve.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 专门描述一个sku的规格名和规格值的类
 */
@Setter
@Getter
public class Spec {

    private String keyId;
    private Integer key;
    private String valueId;
    private Integer value;
}
