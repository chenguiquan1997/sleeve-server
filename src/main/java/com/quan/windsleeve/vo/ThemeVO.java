package com.quan.windsleeve.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ThemeVO {
    private Long id;
    private String name;
    private String description;
    private String title;
    private String tplName;
    private String entranceImg;
    private String titleImg;
    private String internalTopImg;
    private Boolean online;
}
