package com.quan.windsleeve.repository;

import com.quan.windsleeve.model.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Modifying
    @Query("update UserCoupon uc set uc.orderId = :orderId, uc.status = 2 \n" +
            " where uc.userId = :userId and uc.couponId = :couponId \n" +
            "  and uc.status = 1 and uc.orderId is null")
    int cancelAfterVerificationCoupon(@Param("userId") Long userId, @Param("couponId") Long couponId, @Param("orderId") Long orderId);


}
