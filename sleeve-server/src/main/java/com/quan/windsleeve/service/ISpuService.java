package com.quan.windsleeve.service;

import com.quan.windsleeve.model.Category;
import com.quan.windsleeve.model.Spu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISpuService {

    Spu findSpuDetailById(Long id);

    Page<Spu> findSpuPageList(Integer pageNum,Integer pageSize);

    /**
     * 根据分类id查询 spu 数据
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @param isRoot
     * @return
     */
    Page<Spu> findSpuListByCategoryId(Integer pageNum, Integer pageSize, Integer categoryId, Boolean isRoot);
}
