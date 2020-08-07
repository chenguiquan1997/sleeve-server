package com.quan.windsleeve.service;

import com.quan.windsleeve.model.Coupon;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ICouponService {

    List<Coupon> findCouponListByCategotyId(Long categoryId);

    List<Coupon> findByWholeStore(Boolean wholeStore);

    void collectCoupon(Long userId, Long couponId);

    List<Coupon> getMyAvailableCoupon(Long userId);

    List<Coupon> getMyUsedCoupon(Long userId);

    List<Coupon> getMyExpiredCoupon(Long userId);

    List<Map<String,Object>> findMyAvailableCouponWithCategory(Long userId);
}
