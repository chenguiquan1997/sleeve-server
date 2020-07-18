package com.quan.windsleeve.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@Getter
@Setter
public class Theme extends BaseEntity{
    @Id
    private Long id;
    private String name;
    private String description;
    private String title;
    private String tplName;
    private String entranceImg;
    private String titleImg;
    private String internalTopImg;
    private Boolean online;
    private String extend;

    @ManyToMany
    @JoinTable(name = "theme_spu", joinColumns = @JoinColumn(name = "theme_id"),
            inverseJoinColumns = @JoinColumn(name = "spu_id"))
    private List<Spu> spuList;
}
