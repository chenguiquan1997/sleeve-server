package com.quan.windsleeve.api.v1;

import com.quan.windsleeve.core.annotation.ScopeLevel;
import com.quan.windsleeve.core.enums.CouponStatus;
import com.quan.windsleeve.exception.http.NotFoundException;
import com.quan.windsleeve.exception.http.ParamNullException;
import com.quan.windsleeve.model.Coupon;
import com.quan.windsleeve.model.User;
import com.quan.windsleeve.service.impl.CouponServiceImpl;
import com.quan.windsleeve.util.LocalUser;
import com.quan.windsleeve.vo.CouponCategoryVO;
import com.quan.windsleeve.vo.CouponVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    private CouponServiceImpl couponService;

    /**
     * 获取当前分类下的可用优惠券列表
     * @param categoryId
     * @return
     */
    @GetMapping("/by/category/{cid}")
    public List<CouponVO> getCouponListByCateoryId(@PathVariable("cid") Long categoryId) {
        List<Coupon> couponList = couponService.findCouponListByCategotyId(categoryId);
        if(couponList.isEmpty()) {
            return Collections.emptyList();
        }
        return CouponVO.forEachCoupon(couponList);
    }

    /**
     * 查询全场通用券
     * @return List<CouponVO>
     */
    @GetMapping("/whole_store")
    public List<CouponVO> getWholeStoreCouponList() {
        List<Coupon> wholeStoreCouponList = couponService.findByWholeStore(true);
        if(wholeStoreCouponList.isEmpty()) {
            return Collections.emptyList();
        }
        return CouponVO.forEachCoupon(wholeStoreCouponList);
    }

    /**
     * 领取优惠券
     * @param couponId
     * @return
     */
    @PostMapping("/collect/{id}")
    @ResponseBody
    @ScopeLevel
    public String collectCoupon(@PathVariable("id") Long couponId) {
        User userInfo = LocalUser.getUser();
        Long userId = userInfo.getId();
        couponService.collectCoupon(userId,couponId);
        return "领取成功";
    }

    @GetMapping("/myself/by/status/{status}")
    @ScopeLevel
    public List<CouponVO> getMyCouponByStatus(@PathVariable("status") Integer status) {
        User user = LocalUser.getUser();
        Long userId = user.getId();
        if(status == null || userId == null) {
            throw new ParamNullException(70001);
        }
        List<Coupon> couponList = new ArrayList<>();
        switch (CouponStatus.toType(status)) {
            case AVAILABLE:
                couponList = couponService.getMyAvailableCoupon(userId);
                break;
            case USED:
                couponList = couponService.getMyUsedCoupon(userId);
                break;
            case EXPIRED:
                couponList = couponService.getMyExpiredCoupon(userId);
                break;
            default:
                throw new NotFoundException(90001);
        }
        List<CouponVO> couponVoList = CouponVO.forEachCoupon(couponList);
        return couponVoList;
    }

//    @GetMapping("/myself/available/with_category")
//    @ScopeLevel
//    public  List<Map<String,Object>> getMyCouponWithCategory() {
//        User user = LocalUser.getUser();
//        Long userId = user.getId();
//        System.out.println("输出userId: "+userId);
//        if(userId == null) {
//            throw new ParamNullException(70001);
//        }
//        List<Map<String,Object>> categories = couponService.findMyAvailableCouponWithCategory(userId);
//        return categories;
//    }

    @GetMapping("/myself/available/with_category")
    @ScopeLevel
    public  List<CouponCategoryVO> getMyCouponWithCategory() {
        User user = LocalUser.getUser();
        Long userId = user.getId();
        System.out.println("输出userId: "+userId);
        if(userId == null) {
            throw new ParamNullException(70001);
        }
        List<Coupon> couponList = couponService.findMyAvailableCouponWithCategory1(userId);
        List<CouponCategoryVO> couponCategoryVOS = CouponCategoryVO.forEachCoupon(couponList);
        return couponCategoryVOS;
    }
}
