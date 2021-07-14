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

    /**
     * 查询所有上线的六宫格分类数据，条件：
     * (1)一级分类，(2)属于六宫格分类，(3)上线
     * @return
     */
    @Override
    public List<Category> findGridCategory() {
        return categoryRepository.findByIsRootAndIsGridAndGridOnline(1,true,true);
    }
}
