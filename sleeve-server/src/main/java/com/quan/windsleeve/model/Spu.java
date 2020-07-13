package com.quan.windsleeve.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
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

    @OneToMany
    @JoinColumn(name = "spuId")
    private List<SpuDetailImg> spuDetailImgList;

    @OneToMany
    @JoinColumn(name = "spuId")
    private List<SpuImg> spuImgList;

    @OneToMany
    @JoinColumn(name = "spuId")
    private List<Sku> skuList;
}
