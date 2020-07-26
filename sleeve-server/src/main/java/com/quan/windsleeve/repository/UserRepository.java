package com.quan.windsleeve.repository;

import com.quan.windsleeve.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    /**
     * 根据openid查询用户信息
     * @param openid
     * @return
     */
    User findByOpenid(String openid);


}
