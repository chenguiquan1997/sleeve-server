package com.quan.windsleeve.api.v1;

import com.quan.windsleeve.model.Category;
import com.quan.windsleeve.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/grid/all")
    public List<Category> findAllOneLevelCategory() {
        return categoryService.findOneLevelCategory(1);
    }
}
