package com.quan.windsleeve.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @Author Guiquan Chen
 * @Date 2021/8/2 15:00
 * @Version 1.0
 * 热门搜索的实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class HotSearch {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 热门关键字
     */
    private String hotKeyword;
    /**
     * 被搜索次数
     */
    private Integer count;
}
