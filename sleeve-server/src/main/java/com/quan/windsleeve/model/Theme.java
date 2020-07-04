package com.quan.windsleeve.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class Theme {
    @Id
    private Long id;
    private String title;
    private String name;
    @ManyToMany
    private List<Spu> spuList;
}
