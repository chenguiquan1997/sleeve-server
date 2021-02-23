package com.quan.windsleeve.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Where(clause = "delete_time is null")
public class Coupon {
    @Id
    private Long id;
    private String title;
    private Date startTime;
    private Date endTime;
    private BigDecimal fullMoney;
    private BigDecimal minus;
    private BigDecimal rate;
    private String remark;
    private Boolean wholeStore;
    private Integer type;
    private String description;
    private Long activityId;

    //coupon 和 category 是多对多的关系
    @ManyToMany
    @JoinTable(name = "coupon_category",
            joinColumns = @JoinColumn(name = "coupon_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categoryList;
}
