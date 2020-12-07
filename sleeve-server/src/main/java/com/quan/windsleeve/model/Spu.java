package com.quan.windsleeve.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
//加上了@Where注解之后，所有关于Spu实体类的操作，最后在sql语句中，都会加上这一句话
@Where(clause = "delete_time is null and online = 1")
public class Spu extends BaseEntity{
    @Id
    private Long id;
    private String title;
    private String subtitle;
    private Boolean online;
    private String price;
    private Integer sketchSpecId;
    private Integer defaultSkuId;
    private String discountPrice;
    private String description;
    private String img;
    private Boolean isBest;
    private String spuThemeImg;
    private String forThemeImg;
    private Integer categoryId;
    private Integer rootCategoryId;
    private String tags;
    /**
     * 商品详情图
     */
    @OneToMany
    @JoinColumn(name = "spuId")
    private List<SpuDetailImg> spuDetailImgList;
    /**
     * 商品轮播图
     */
    @OneToMany
    @JoinColumn(name = "spuId")
    private List<SpuImg> spuImgList;

    @OneToMany
    @JoinColumn(name = "spuId")
    private List<Sku> skuList;
}
