package com.quan.windsleeve.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@Where(clause = "delete_time is null and online = 1")
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
    private Integer idx;
    private String gridImg;
    private Boolean isGrid;

    //coupon 和 category 是多对多的关系
//    @ManyToMany
//    private List<Coupon> couponList;
}
