package com.quan.windsleeve.repository;

import com.quan.windsleeve.model.Spu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpuRepository extends JpaRepository<Spu,Long> {
    /**
     * 根据 spu id 查询详情
     * @param id
     * @return
     */
    Spu findOneById(Long id);

    /**
     * 对 spu 进行分页查询
     * @param pageable
     * @return 承装spu数据的 page 对象
     */
    @Override
    Page<Spu> findAll(Pageable pageable);

    /**
     * 根据categoryId查询二级下面spu数据，并按照创建时间进行分页查询
     * @param pageable
     * @param categoryId
     * @return
     */
    Page<Spu> findByCategoryIdOrderByCreateTimeDesc(Pageable pageable,Integer categoryId);

    /**
     *
     * @param pageable
     * @param rootCategoryId
     * @return
     */
    Page<Spu> findByRootCategoryIdOrderByCreateTimeDesc(Pageable pageable, Integer rootCategoryId);

}
