package com.quan.windsleeve.repository;

import com.quan.windsleeve.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity,Long> {

    /**
     * 通过活动名获取一条完整的activity数据
     * @param name
     * @return
     */
    Activity findOneByName(String name);

    /**
     * 根据 activityId 查询一条完整的 activity 数据
     * @param activityId
     * @return
     */
    Activity findOneById(Long activityId);
}
