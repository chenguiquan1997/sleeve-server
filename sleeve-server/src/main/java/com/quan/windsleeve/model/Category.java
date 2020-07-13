package com.quan.windsleeve.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class Category extends BaseEntity{
    @Id
    private Long id;
    private String name;
    private String description;
    private Integer isRoot;
    private Boolean online;
    private Integer parentId;
    private String img;
    private Integer level;
    private Integer index;



}
