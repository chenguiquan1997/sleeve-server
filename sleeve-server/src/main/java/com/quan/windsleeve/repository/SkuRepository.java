package com.quan.windsleeve.repository;

import com.quan.windsleeve.model.Sku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkuRepository extends JpaRepository<Sku,Long> {

    /**
     * 根据id集合查询sku
     * @param skuIdList
     * @return
     */
    List<Sku> findAllByIdIn(List<Long> skuIdList);

    @Modifying
    @Query("update Sku s set s.stock = s.stock - :count\n" +
            "where s.id = :skuId and s.stock >= :count")
    int reduceStock(@Param("skuId") Long skuId, @Param("count") Integer count);

    @Modifying
    @Query("update Sku s set s.stock = s.stock + :count\n" +
            " where s.id = :skuId")
    int revertStock(@Param("skuId") Long skuId, @Param("count") Integer count);
}
