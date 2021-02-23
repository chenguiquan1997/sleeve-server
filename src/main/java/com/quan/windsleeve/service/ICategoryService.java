package com.quan.windsleeve.service;

import com.quan.windsleeve.model.Category;

import java.util.List;
import java.util.Map;

public interface ICategoryService {

    /**
     * 根据isRoot查询所有级别的分类
     * @return Map<String,List<Category>>
     */
    Map<String,List<Category>> findAllCategory();

    /**
     * 查询一级分类
     * @return
     */
    List<Category> findGridCategory();

}
