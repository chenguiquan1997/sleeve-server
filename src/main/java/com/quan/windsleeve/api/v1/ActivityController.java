package com.quan.windsleeve.api.v1;

import com.quan.windsleeve.model.Activity;
import com.quan.windsleeve.model.Coupon;
import com.quan.windsleeve.service.impl.ActivityServiceImpl;
import com.quan.windsleeve.util.CouponUtils;
import com.quan.windsleeve.vo.ActivityCouponVO;
import com.quan.windsleeve.vo.ActivityPureVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityServiceImpl activityService;

    /**
     * 根据活动名，获取当前活动信息数据
     * @param name 活动名称->对应activity表的name字段
     * @return
     */
    @GetMapping("/name/{name}")
    public ActivityPureVO getActivityByName(@PathVariable String name) {

        Activity activity = activityService.findActivityByName(name);
        ActivityPureVO pureVO = new ActivityPureVO(activity);
//        BeanUtils.copyProperties(activity,pureVO);
        return pureVO;
    }

    /**
     * 获取当前活动的所有优惠券，并携带优惠券信息
     * @param name 活动名称 -> 对应activity中的name字段
     * @return
     */
    @GetMapping("/name/{name}/with_coupon")
    public ActivityCouponVO getActivityCouponsByName(@PathVariable String name) {
        Activity activity = activityService.findActivityByName(name);
        List<Coupon> filterCouponList = CouponUtils.filterCoupons(activity.getCouponList());
        activity.setCouponList(filterCouponList);
        ActivityCouponVO couponVO = new ActivityCouponVO(activity);
        for(Coupon coupon : couponVO.getCouponList()) {
            coupon.setCategoryList(null);
        }
        return couponVO;
    }


}
