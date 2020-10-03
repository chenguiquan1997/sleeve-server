package com.quan.windsleeve.manager.rocketMq;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface RocketMqRepository extends JpaRepository<FailMsg,Long> {

    @Transactional
    @Modifying
    @Query("update FailMsg f set f.deleteTime = :time where f.keyId = :keyId")
    int updateSendSuccessMsg(@Param("time") Date currTime, @Param("keyId") String keyId);
}
