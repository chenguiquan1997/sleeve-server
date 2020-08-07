package com.quan.windsleeve.repository;

import com.quan.windsleeve.model.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCouponRepository extends JpaRepository<UserCoupon,Long> {

    /**
     * 根据 userId 和 couponId 查询一条记录
     * @param userId
     * @param couponId
     * @return
     */
    UserCoupon findOneByUserIdAndCouponId(Long userId, Long couponId);


}
