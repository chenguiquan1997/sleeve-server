package com.quan.windsleeve.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class BannerItem {
    @Id
    private long id;
    private String img;
    private String keyword;
    private Short type;
    private String name;

    private long bannerId;
}
