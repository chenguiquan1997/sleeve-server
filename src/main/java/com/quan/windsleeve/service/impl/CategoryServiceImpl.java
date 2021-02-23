package com.quan.windsleeve.service.impl;

import com.quan.windsleeve.model.Category;
import com.quan.windsleeve.repository.CategoryRepository;
import com.quan.windsleeve.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     *查询所有的分类信息
     */
    @Override
    public Map<String,List<Category>> findAllCategory() {
        List<Category> roots = categoryRepository.findByisRoot(1);
        List<Category> subs = categoryRepository.findByisRoot(0);
        Map<String,List<Category>> categoryMap = new HashMap<>();
        categoryMap.put("roots",roots);
        categoryMap.put("subs",subs);
        return categoryMap;
    }

    @Override
    public List<Category> findGridCategory() {
        return categoryRepository.findByisRoot(1);
    }
}
