package com.quan.windsleeve.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryVO {

    private Long id;
    private String name;
    private String description;
    private Integer isRoot;
    private Boolean online;
    private Integer parentId;
    private String img;
    private Integer level;
    private Integer idx;

}
