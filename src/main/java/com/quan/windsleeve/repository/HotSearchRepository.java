package com.quan.windsleeve.repository;

import com.quan.windsleeve.model.HotSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author Guiquan Chen
 * @Date 2021/8/2 15:13
 * @Version 1.0
 */
@Repository
public interface HotSearchRepository extends JpaRepository<HotSearch,Long> {

    HotSearch findByHotKeyword(String text);

    /**
     * 将当前关键字的搜索数量+1
     * @param keyword
     * @return
     */
    @Transactional
    @Modifying
    @Query("update HotSearch hs set hs.count = hs.count+1 where hs.hotKeyword = :keyword")
    int updateCountByKeyword(@Param("keyword") String keyword);
}
