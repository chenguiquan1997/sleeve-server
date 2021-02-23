package com.quan.windsleeve.service.impl;

import com.quan.windsleeve.core.enums.CouponStatus;
import com.quan.windsleeve.exception.http.NotFoundException;
import com.quan.windsleeve.exception.http.SourceAlreadyExistException;
import com.quan.windsleeve.exception.http.UnUsedException;
import com.quan.windsleeve.model.Activity;
import com.quan.windsleeve.model.Coupon;
import com.quan.windsleeve.model.UserCoupon;
import com.quan.windsleeve.repository.ActivityRepository;
import com.quan.windsleeve.repository.CouponRepository;
import com.quan.windsleeve.repository.UserCouponRepository;
import com.quan.windsleeve.service.ICouponService;
import com.quan.windsleeve.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CouponServiceImpl implements ICouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    /**
     * 根据分类id查询当前分类可以使用的所有优惠券
     * @param categoryId
     * @return
     */
    @Override
    public List<Coupon> findCouponListByCategotyId(Long categoryId) {
        Date nowDate = new Date();
        List<Coupon> coupons = couponRepository.findCouponListByCategotyId(categoryId,nowDate);
        List<Coupon> newCoupons = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        for(Coupon c : coupons) {
           if(!ids.contains(c.getId())) {
               ids.add(c.getId());
               newCoupons.add(c);
           }
        }
        return newCoupons;
    }

    /**
     * 根据 whole_store字段 查询全场通用券，"1"表示全场通用，"0"表示其他类型优惠券
     * @param wholeStore
     * @return
     */
    @Override
    public List<Coupon> findByWholeStore(Boolean wholeStore) {
        return couponRepository.findAllByWholeStore(wholeStore);
    }

    /**
     * 领取优惠券
     * @param userId
     * @param couponId
     */
    @Override
    public void collectCoupon(Long userId, Long couponId) {
        //查找当前couponId的优惠券是否存在
        Coupon coupon = couponRepository.findOneById(couponId);
        if(coupon == null) {
            throw new NotFoundException(90001);
        }

        //判断当前 coupon 所属的 activity 是否有效
        Long activityId = coupon.getActivityId();
        Activity activity = activityRepository.findOneById(activityId);
        if(activity == null) {
            throw new NotFoundException(40001);
        }
        Date startTime = activity.getStartTime();//活动开始时间
        Date endTime = activity.getEndTime();//活动结束时间
        Date currTime = new Date();//当前时间
        Boolean bool = CommonUtils.judgeActivityIsValid(startTime, endTime, currTime);
        if(bool == false) {
            throw new UnUsedException(40002);
        }

        //判断当前用户是否已经领取过该优惠券
        UserCoupon userCoupon = userCouponRepository.findOneByUserIdAndCouponId(userId, couponId);
        if(userCoupon != null) {
            throw new SourceAlreadyExistException(90002);
        }

        //正式执行用户领取优惠券的操作
        UserCoupon userCoupon1 = UserCoupon.builder()
                .userId(userId)
                .couponId(couponId)
                .status(CouponStatus.AVAILABLE.getCode())
                .createTime(currTime)
                .build();
        userCouponRepository.save(userCoupon1);
    }

    @Override
    public List<Coupon> getMyAvailableCoupon(Long userId) {
        Date currTime = new Date();
        return couponRepository.findMyAvailableCoupon(userId,currTime);
    }

    @Override
    public List<Coupon> getMyUsedCoupon(Long userId) {
        return couponRepository.findMyUsedCoupon(userId);
    }

    @Override
    public List<Coupon> getMyExpiredCoupon(Long userId) {
        Date currTime = new Date();
        return couponRepository.findMyExpiredCoupon(userId,currTime);
    }

    @Override
    public List<Map<String,Object>> findMyAvailableCouponWithCategory(Long userId) {
        Date currTime = new Date();
        List<Map<String,Object>> couponAndCategoryList = couponRepository.findMyAvailableCouponWithCategory(userId,currTime);
        List<Integer> flag = new ArrayList<>();
        List<Map<String,Object>> result = new ArrayList<>();
        couponAndCategoryList.forEach(couponAndCategory->{
            Integer couponId = (Integer) couponAndCategory.get("id");
            int index = flag.indexOf(couponId);
            //如果等于-1，证明当前集合中没有该元素
            if(index == -1) {
                //承装优惠券数据
                HashMap<String,Object> couponMap = new HashMap<>();
                couponMap.putAll(couponAndCategory);
                couponMap.remove("name");
                couponMap.remove("categoryId");
                //承装适用于当前优惠券的list集合
                List<Map<String,Object>> categories = new ArrayList<>();
                Map<String,Object> categoryMap = new HashMap<>();
                categoryMap.put("id",couponAndCategory.get("categoryId"));
                categoryMap.put("name",couponAndCategory.get("name"));
                categories.add(categoryMap);
                couponMap.put("categories",categories);
                result.add(couponMap);
                flag.add(couponId);
            }else {
                Map<String,Object> categoryMap = new HashMap<>();
                categoryMap.put("id",couponAndCategory.get("categoryId"));
                categoryMap.put("name",couponAndCategory.get("name"));
                //遍历List集合中的Map,找到与couponId相等的那个优惠券
                result.forEach(couponMap->{
                    if(couponId == couponMap.get("id")) {
                        List<Map<String,Object>> categories = (List<Map<String, Object>>) couponMap.get("categories");
                        categories.add(categoryMap);
                    }
                });
            }
        });
        return result;
    }

    public List<Coupon> findMyAvailableCouponWithCategory1(Long userId) {
        Date currTime = new Date();
        return couponRepository.findMyAvailableCouponWithCategory1(userId,currTime);
    }
}
