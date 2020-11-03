package com.quan.windsleeve.repository;

import com.quan.windsleeve.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,Long> {

    /**
     * 根据分类id查询当前分类可以使用的所有优惠券
     * @param categoryId
     * @return
     * 如果需要将参数赋值到sql语句中，那么需要在@Param()中，定义参数名name，然后在sql中使用:name注入
     */
    @Query(value = "select * from coupon c \n" +
            " join coupon_category cc on c.id = cc.coupon_id \n" +
            " join category ca on cc.category_id = ca.id or cc.root_category_id = ca.parent_id\n" +
            " join activity a on c.activity_id = a.id\n" +
            " where ca.id = :categoryId and a.start_time < :now and a.end_time > :now\n" +
            "       and c.start_time < :now and c.end_time > :now",
            nativeQuery = true)
    List<Coupon> findCouponListByCategotyId(@Param("categoryId") Long categoryId, @Param("now") Date now);

    /**
     * 根据 wholeStore 查询全场通用券
     * @param wholeStore
     * @return
     */
    List<Coupon> findAllByWholeStore(Boolean wholeStore);

    /**
     * 根据 couponId 查询优惠券
     * @param couponId
     * @return
     */
    Coupon findOneById(Long couponId);

    /**
     * 获取用户当前可以使用的优惠券列表
     * @param userId
     * @param now
     * @return
     */
    @Query(value = "select c.* from coupon c\n" +
            "join user_coupon uc on c.id = uc.coupon_id\n" +
            "where c.start_time < :now and c.end_time > :now \n" +
            "      and uc.status = 1 and uc.order_id is NULL\n" +
            "      and uc.user_id = :userId",nativeQuery = true)
    List<Coupon> findMyAvailableCoupon(@Param("userId") Long userId, @Param("now") Date now);

    @Query(value = "select c.* from coupon c \n" +
            "join user_coupon uc on c.id = uc.coupon_id \n" +
            "where uc.status = 2 and uc.order_id is not null\n" +
            "      and uc.user_id = :userId", nativeQuery = true)
    List<Coupon> findMyUsedCoupon(@Param("userId") Long userId);

    @Query(value = "select c.* from coupon c \n" +
            "join user_coupon uc on c.id = uc.coupon_id\n" +
            "where uc.status <> 2 and uc.order_id is null \n" +
            "      and c.end_time < :now and uc.user_id = :userId", nativeQuery = true)
    List<Coupon> findMyExpiredCoupon(@Param("userId") Long userId, @Param("now") Date now);

    @Query(value = "select distinct c.*, ca.id categoryId, ca.name name from coupon c\n" +
            "  join user_coupon uc on c.id = uc.coupon_id \n" +
            "  join coupon_category cc on c.id = cc.coupon_id\n" +
            "  join category ca on ca.id = cc.category_id or ca.id = cc.root_category_id\n" +
            "  where c.start_time < :now and c.end_time > :now \n" +
            "        and uc.status = 1 and uc.order_id is null and uc.user_id = :userId", nativeQuery = true)
    List<Map<String,Object>> findMyAvailableCouponWithCategory(@Param("userId") Long userId, @Param("now") Date now);

    @Query(value = "select c from Coupon c \n" +
            "join UserCoupon uc on c.id = uc.couponId\n" +
            "join User u on u.id = uc.userId\n" +
            "where uc.status = 1 and uc.orderId is null\n" +
            "      and c.startTime < :now and c.endTime > :now\n" +
            "      and u.id = :userId")
    List<Coupon> findMyAvailableCouponWithCategory1(@Param("userId") Long userId, @Param("now") Date now);
}
