package com.quan.windsleeve.vo;

import com.quan.windsleeve.model.Category;
import com.quan.windsleeve.model.Coupon;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class CouponCategoryVO {

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

    private List<Category> categoryList;

    public static List<CouponCategoryVO> forEachCoupon(List<Coupon> couponList) {
        List<CouponCategoryVO> couponCategoryVOS = new ArrayList<>();
        couponList.forEach(coupon -> {
            CouponCategoryVO couponCategoryVO = new CouponCategoryVO();
            BeanUtils.copyProperties(coupon,couponCategoryVO);
            couponCategoryVOS.add(couponCategoryVO);
        });
        return couponCategoryVOS;
    }
}
