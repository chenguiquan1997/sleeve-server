package com.quan.windsleeve.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author Guiquan Chen
 * @Date 2021/5/31 16:54
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MinUserDTO {

    @NotEmpty
    private String nickName;

    @NotNull
    private Integer gender;

    @NotEmpty
    private String city;

    @NotEmpty
    private String province;

    @NotEmpty
    private String country;

    @NotEmpty
    private String avatarUrl;
}
