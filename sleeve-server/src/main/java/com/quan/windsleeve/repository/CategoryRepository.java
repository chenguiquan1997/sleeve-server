package com.quan.windsleeve.repository;

import com.quan.windsleeve.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    /**
     * 查询所有的一级分类商品
     * @param rootId
     * @return
     */
    List<Category> findByisRoot(Integer rootId);
}
