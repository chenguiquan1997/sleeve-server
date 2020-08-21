package com.quan.windsleeve.logic;

import com.quan.windsleeve.bo.OrderSkuBO;
import com.quan.windsleeve.core.enums.CouponStatus;
import com.quan.windsleeve.core.enums.CouponType;
import com.quan.windsleeve.core.money.HalfEvenRound;
import com.quan.windsleeve.core.money.IMoneyDiscount;
import com.quan.windsleeve.exception.http.MoneyNotEqualsException;
import com.quan.windsleeve.exception.http.NotFoundException;
import com.quan.windsleeve.exception.http.TypeNotMatchException;
import com.quan.windsleeve.exception.http.UnUsedException;
import com.quan.windsleeve.model.Category;
import com.quan.windsleeve.model.Coupon;
import com.quan.windsleeve.model.UserCoupon;
import com.quan.windsleeve.repository.CouponRepository;
import com.quan.windsleeve.repository.UserCouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Component
//开启多例模式，设置当前属性之后，
@Scope("prototype")
public class CouponChecker {

    private Coupon coupon;

    private UserCoupon userCoupon;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    /**
     * 校验优惠券是否可以使用
     * @param userId
     * @param couponId
     */
    public void checkCoupon(Long userId, Long couponId) {
        Coupon coupon = couponRepository.findOneById(couponId);
        if(coupon == null) {
            throw new NotFoundException(90001);
        }
        //将coupon注入到当前属性
        this.coupon = coupon;

        //根据userId 和 couponId 获取用户的优惠券
        UserCoupon userCoupon = userCouponRepository.findOneByUserIdAndCouponId(userId,couponId);
        if(userCoupon == null) {
            throw new NotFoundException(10004);
        }
        this.userCoupon = userCoupon;

        //校验当前优惠券是否已经被使用过
        boolean usedFlag = couponIsUsed(userCoupon);
        if(!usedFlag) {
            throw new UnUsedException(90003);
        }

        //判断当前优惠券是否到了可以使用的日期
        boolean arriveFlag = couponArriveUse(coupon);
        if(!arriveFlag) {
            throw new UnUsedException(90004);
        }

        //判断当前优惠券是否过期
        boolean expireFlag = couponIsExpire(coupon);
        if(!expireFlag) {
            throw new UnUsedException(90005);
        }
    }

    /**
     * 校验当前优惠券是否已经被使用过
     * @param userCoupon
     * @return
     */
    private boolean couponIsUsed(UserCoupon userCoupon) {
        Integer status = userCoupon.getStatus();
        Long orderId = userCoupon.getOrderId();
        if(status.equals(CouponStatus.AVAILABLE.getCode()) && orderId == null) {
            return true;
        }
        return false;
    }

    /**
     * 判断当前优惠券是否到了可以使用的日期
     * @param coupon
     * @return
     */
    private boolean couponArriveUse(Coupon coupon) {
        Date currTime = new Date();
        Date couponStratTime = coupon.getStartTime();
        if(currTime.getTime() >= couponStratTime.getTime()) {
            return true;
        }
        return false;
    }

    /**
     * 判断当前优惠券是否过期
     * @param coupon
     * @return
     */
    private boolean couponIsExpire(Coupon coupon) {
        Date currTime = new Date();
        Date couponEndTime = coupon.getEndTime();
        if(currTime.getTime() <= couponEndTime.getTime()) {
            return true;
        }
        return false;
    }

    /**
     * 判断服务端计算的最后金额与前端传入的最后金额是否一致
     * @param orderFinalTotalPrice 前端传入的最后金额
     * @param serverTotalPrice 服务端计算的应付金额，还没有去掉优惠券金额
     */
    public void finalTotalPriceIsOk(BigDecimal orderFinalTotalPrice,
                                    BigDecimal serverTotalPrice) {

        switch (CouponType.getType(this.coupon.getType())) {
            case FULL_MINUS:
                BigDecimal serverFinalTotalPrice1 = serverTotalPrice.subtract(coupon.getMinus());
                // != 0 证明 server端与前端计算的金额不相等
                System.out.println("后端扣除优惠券计算的总金额："+serverFinalTotalPrice1);
                System.out.println("前端传入的应付最后总金额："+orderFinalTotalPrice);
                if(serverFinalTotalPrice1.compareTo(orderFinalTotalPrice) != 0) {
                    throw new MoneyNotEqualsException(50001);
                }
                break;
            case DISCOUNT:
                IMoneyDiscount moneyDiscount = new HalfEvenRound();
                BigDecimal serverFinalTotalPrice2 = moneyDiscount.discount(serverTotalPrice,coupon.getRate());
                System.out.println("后端扣除优惠券计算的总金额："+serverFinalTotalPrice2);
                System.out.println("前端传入的应付最后总金额："+orderFinalTotalPrice);
                if(serverFinalTotalPrice2.compareTo(orderFinalTotalPrice) != 0) {
                    throw new MoneyNotEqualsException(50001);
                }
                break;
            case NO_AMBIT:
                BigDecimal serverFinalTotalPrice3 = serverTotalPrice.subtract(coupon.getMinus());
                System.out.println("后端扣除优惠券计算的总金额："+serverFinalTotalPrice3);
                System.out.println("前端传入的应付最后总金额："+orderFinalTotalPrice);
                if(serverFinalTotalPrice3.compareTo(orderFinalTotalPrice) != 0) {
                    throw new MoneyNotEqualsException(50001);
                }
                break;
            default:
                throw new TypeNotMatchException(90006);
        }


    }

    /**
     * 需要计算当前订单中，所有属于可用优惠券分类下的商品的金额总和，是否达到当前优惠券
     * 可以使用的门槛
     * @param orderSkuBOList
     */
    public void couponIsArriveThreshold(List<OrderSkuBO> orderSkuBOList,BigDecimal currentOrderTotalPrice) {
        //属于优惠券分类下的所有商品的金额总和
        BigDecimal serverCouponTotalPrice = new BigDecimal("0");
        //首先需要判断当前优惠券的类型
        //1.如果当前优惠券是：折扣券 或 满减券，并且是全场通用的
        if((this.coupon.getType().equals(CouponType.DISCOUNT.getCode()) || this.coupon.getType().equals(CouponType.FULL_MINUS.getCode())) && this.coupon.getWholeStore()) {
           //如果优惠券是全场券，那么优惠金额基数 = 当前订单总价格
            serverCouponTotalPrice = currentOrderTotalPrice;
            if(serverCouponTotalPrice.min(this.coupon.getFullMoney()).equals(serverCouponTotalPrice)) {
                //证明不满足满减的门槛
                throw new UnUsedException(90007);
            }
        }
        //2.如果当前优惠券是：满减券 或 折扣券，不是全场通用的，并且有适用的分类
        else if((this.coupon.getType().equals(CouponType.FULL_MINUS.getCode()) || this.coupon.getType().equals(CouponType.DISCOUNT.getCode())) && !this.coupon.getWholeStore()) {
            //获取可以使用当前优惠券的商品分类集合
            List<Category> categoryList = this.coupon.getCategoryList();
            serverCouponTotalPrice = fullMinusTotalPrice(orderSkuBOList,categoryList);
            if(serverCouponTotalPrice.min(this.coupon.getFullMoney()).equals(serverCouponTotalPrice)) {
                //证明不满足满减的门槛
                throw new UnUsedException(90007);
            }
        }

    }

    /**
     * 可以使用满减券的门槛金额计算
     * @param orderSkuBOList
     * @param categoryList
     * @return
     */
    private BigDecimal fullMinusTotalPrice(List<OrderSkuBO> orderSkuBOList, List<Category> categoryList) {
        BigDecimal serverCouponTotalPrice = new BigDecimal("0.00");
        //找到当前订单中，属于categoryList分类的所有sku
        for(OrderSkuBO skuBO : orderSkuBOList) {
            for(Category category: categoryList) {
                //如果当前sku所属的分类id = 优惠券可以使用范围中的categoryId
                if(skuBO.getCategoryId() == category.getId()) {
                    BigDecimal currSkuPrice = skuBO.getActualPrice().multiply(new BigDecimal(skuBO.getCount()));
                    serverCouponTotalPrice = serverCouponTotalPrice.add(currSkuPrice);
                }
            }
        }
        return serverCouponTotalPrice;
    }

}
