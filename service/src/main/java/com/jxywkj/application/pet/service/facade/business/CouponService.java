package com.jxywkj.application.pet.service.facade.business;

import com.jxywkj.application.pet.model.consign.Coupon;

import java.util.List;

/**
 * @ClassName BusinessService
 * @Description: 优惠券
 * @Author Aze
 * @Date 2019/8/14 13:43
 * @Version 1.0
 **/
public interface CouponService {
    /**
     * @Author LiuXiangLin
     * @Description 核销券
     * @Date 17:38 2019/8/29
     * @Param [couponNo,businessNo]
     * @return int
     **/
    int verificationCoupon(String couponNo,String businessNo);

    /**
     * @Author LiuXiangLin
     * @Description 通过券主键查询数据
     * @Date 16:55 2019/9/4
     * @Param [couponNo]
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Coupon>
     **/
    List<Coupon> listByCouponNo(String couponNo);

}
