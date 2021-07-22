package com.quan.windsleeve.repository;

import com.quan.windsleeve.model.MinProUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author Guiquan Chen
 * @Date 2021/6/1 8:55
 * @Version 1.0
 */
@Repository
public interface MinUserRepository extends JpaRepository<MinProUser,Long> {

    /**
     * 根据 昵称 查询用户信息
     * @param nickName
     * @return
     */
    MinProUser findByNickName(String nickName);
}
