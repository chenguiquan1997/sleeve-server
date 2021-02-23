package com.quan.windsleeve.util;

import com.quan.windsleeve.model.Coupon;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CouponUtils {

    public static List<Coupon> filterCoupons(List<Coupon> couponList) {
        if(couponList == null || couponList.size() == 0) return null;
        List<Coupon> newCouponList = new ArrayList<>();
        Date currTime = new Date();
        for(Coupon coupon: couponList) {
            Date endTime = coupon.getEndTime();
            //证明当前优惠券已经过期
            if(endTime.before(currTime)) {
               continue;
            }
            newCouponList.add(coupon);
        }
        return newCouponList;
    }
}
