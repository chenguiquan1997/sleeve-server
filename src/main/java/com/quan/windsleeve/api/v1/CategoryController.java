package com.quan.windsleeve.api.v1;

import com.quan.windsleeve.exception.http.NotFoundException;
import com.quan.windsleeve.model.Category;
import com.quan.windsleeve.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    /**
     * 获取所有的商品分类数据
     * @return
     */
    @GetMapping("/all")
    public Map<String,List<Category>> findAllCategory() {
        Map<String,List<Category>> categoryMap = categoryService.findAllCategory();
        if(categoryMap == null) {
            throw new NotFoundException(80001);
        }
        return categoryMap;
    }

    /**
     * 获取六宫格数据
     * @return
     */
    @GetMapping("/grid/all")
    public List<Category> getGridCategory() {
        return categoryService.findGridCategory();
    }
}
