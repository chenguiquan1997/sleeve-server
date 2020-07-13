package com.quan.windsleeve.repository;

import com.quan.windsleeve.model.Category;
import com.quan.windsleeve.model.Spu;
import org.springframework.data.domain.Example;
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



}
