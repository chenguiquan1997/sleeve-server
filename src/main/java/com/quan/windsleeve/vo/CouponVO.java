package com.quan.windsleeve.vo;

import com.quan.windsleeve.model.Coupon;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CouponVO {

    private Long id;
    private String title;
    private Date startTime;
    private Date endTime;
    private BigDecimal fullMoney;
    private BigDecimal minus;
    private BigDecimal rate;
    private String remark;
    private Boolean wholeStore;
    private Integer type;
    private String description;

    public static List<CouponVO> forEachCoupon(List<Coupon> couponList) {
        List<CouponVO> couponVOList = new ArrayList<>();
        couponList.forEach(coupon-> {
           CouponVO couponVO = new CouponVO();
           BeanUtils.copyProperties(coupon,couponVO);
           couponVOList.add(couponVO);
        });
        return couponVOList;
    }
}
