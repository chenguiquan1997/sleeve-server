package com.quan.windsleeve.repository;

import com.quan.windsleeve.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    /**
     * 根据isRoot查询不同级别的分类
     * @param isRoot
     * @return
     */
    List<Category> findByisRoot(Integer isRoot);




}
