package com.jxywkj.application.pet.dao.business;

import com.jxywkj.application.pet.model.consign.Coupon;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName CouponMapper
 * @Description: 优惠券
 * @Author Aze
 * @Date 2019/8/14 17:41
 * @Version 1.0
 **/
@Component
public interface CouponMapper {
    /**
     * @Author LiuXiangLin
     * @Description 核销券
     * @Date 17:41 2019/8/29
     * @Param [coupon]
     * @return int
     **/
    int verificationCoupon(@Param("coupon") Coupon coupon);


    /**
     * @Author LiuXiangLin
     * @Description 通过券主键查询券列表
     * @Date 16:56 2019/9/4
     * @Param [couponNo]
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Coupon>
     **/
    List<Coupon> listByCouponNo(@Param("couponNo")String couponNo);

}
