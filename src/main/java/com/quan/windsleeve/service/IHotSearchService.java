package com.quan.windsleeve.service;

import com.quan.windsleeve.model.HotSearch;

import java.util.List;

/**
 * @Author Guiquan Chen
 * @Date 2021/8/2 15:16
 * @Version 1.0
 */
public interface IHotSearchService {

    // 获取前10个被搜索次数最多的关键字
    List<String> getkeywordTop10();

    void addHotProductrecords(String keyword);
}
