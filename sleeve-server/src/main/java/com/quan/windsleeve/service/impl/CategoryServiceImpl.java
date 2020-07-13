package com.quan.windsleeve.service.impl;

import com.quan.windsleeve.model.Category;
import com.quan.windsleeve.repository.CategoryRepository;
import com.quan.windsleeve.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findOneLevelCategory(Integer rootId) {
        return categoryRepository.findByisRoot(rootId);
    }
}
