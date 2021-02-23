package com.quan.windsleeve.vo;

import com.quan.windsleeve.model.Activity;
import com.quan.windsleeve.model.Coupon;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ActivityCouponVO {
//    List<Coupon> couponList;
//
//    public ActivityCouponVO(Activity activity) {
//        super(activity);
//        this.couponList = activity.getCouponList().stream()
//                .map(Coupon::new).collect(Collectors.toList());
//    }

    private Long id;
    private String name;
    private String description;
    private Date startTime;
    private Date endTime;
    private String remark;
    private Boolean online;
    private String entranceImg;
    private String internalTopImg;
    private List<Coupon> couponList;

    public ActivityCouponVO(Activity activity) {
        BeanUtils.copyProperties(activity,this);

    }
}
