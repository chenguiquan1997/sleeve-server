package com.quan.windsleeve.service;

import com.quan.windsleeve.model.Category;

import java.util.List;

public interface ICategoryService {

    List<Category> findOneLevelCategory(Integer rootId);
}
