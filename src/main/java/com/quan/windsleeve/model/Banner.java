package com.quan.windsleeve.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
public class Banner {
    @Id
    private long id;
    private String name;
    private String title;
    private String img;
    private String description;
    @OneToMany
    @JoinColumn(name = "bannerId")
    private List<BannerItem> bannerItems;
}
