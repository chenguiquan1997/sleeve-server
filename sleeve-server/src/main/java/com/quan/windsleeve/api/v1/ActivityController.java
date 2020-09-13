package com.quan.windsleeve.api.v1;

import com.quan.windsleeve.model.Activity;
import com.quan.windsleeve.service.impl.ActivityServiceImpl;
import com.quan.windsleeve.vo.ActivityCouponVO;
import com.quan.windsleeve.vo.ActivityPureVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityServiceImpl activityService;

    /**
     * 根据活动名，获取当前活动信息数据
     * @param name
     * @return
     */
    @GetMapping("/name/{name}")
    public ActivityPureVO getActivityByName(@PathVariable String name) {

        Activity activity = activityService.findActivityByName(name);
        ActivityPureVO pureVO = new ActivityPureVO(activity);
//        BeanUtils.copyProperties(activity,pureVO);
        return pureVO;
    }

    @GetMapping("/name/{name}/with_coupon")
    public ActivityCouponVO getActivityCouponsByName(@PathVariable String name) {
        Activity activity = activityService.findActivityByName(name);
        ActivityCouponVO couponVO = new ActivityCouponVO(activity);
        return couponVO;
    }
}
