package com.quan.windsleeve.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Where(clause = "delete_time is null")
public class Banner extends BaseEntity{
    @Id
    private Long id;
    private String name;
    private String title;
    private String img;
    private String description;

    //定义当前获取关联数据的方式为懒加载
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "bannerId")
    private List<BannerItem> items;
}
