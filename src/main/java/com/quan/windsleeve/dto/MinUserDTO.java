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
    /**
     * 昵称
     */
    @NotEmpty
    private String nickName;
    /**
     * 性别
     */
    @NotNull
    private Integer gender;
    /**
     * 城市，如果用户选择的country为非中国，那么city 和 province 为空
     */
    private String city;
    /**
     * 省份
     */
    private String province;
    /**
     * 国家
     */
    @NotEmpty
    private String country;
    /**
     * 头像
     */
    @NotEmpty
    private String avatarUrl;
}
