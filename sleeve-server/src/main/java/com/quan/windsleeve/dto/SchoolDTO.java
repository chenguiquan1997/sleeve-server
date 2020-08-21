package com.quan.windsleeve.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
public class SchoolDTO {
    @Range(min = 0,max=150)
    private Integer score;
}
