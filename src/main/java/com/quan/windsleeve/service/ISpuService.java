package com.quan.windsleeve.service;

import com.quan.windsleeve.model.Category;
import com.quan.windsleeve.model.Spu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISpuService {

    Spu findSpuDetailById(Long id);

    /**
     * 查询Home页瀑布流
     * @param pageNum 页码
     * @param pageSize 每页数据量
     * @return
     */
    Page<Spu> findSpuPageList(Integer pageNum,Integer pageSize);

    /**
     * 根据分类id查询 spu 数据
     * @param pageNum 页码
     * @param pageSize 当前页数据
     * @param categoryId 分类id
     * @param isRoot 是否为一级分类
     * @return
     */
    Page<Spu> findSpuListByCategoryId(Integer pageNum, Integer pageSize, Integer categoryId, Boolean isRoot);

    Page<Spu> findSpuListByLuenceIds(Integer pageNum, Integer pageSize, List<Long> ids);
}
