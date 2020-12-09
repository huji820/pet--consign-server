package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.Coupon;

import java.util.List;

/**
 * @ClassName CustomerCouponService
 * @Description 优惠券
 * @Author LiuXiangLin
 * @Date 2019/8/20 14:21
 * @Version 1.0
 **/
public interface CustomerCouponService {
    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 添加一张优惠券
     * @Date 14:21 2019/8/20
     * @Param [coupon]
     **/
    int addCoupon(Coupon coupon);

    /**
     * <p>
     * 通过用户编号查询用户所有的优惠券
     * </p>
     *
     * @param customerNo 用户编号
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Coupon>
     * @author LiuXiangLin
     * @date 16:36 2020/3/4
     **/
    List<Coupon> listByCustomerNo(String customerNo);

    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 获取通过下单获取的优惠券
     * @Date 16:35 2019/8/27
     * @Param [orderNo]
     **/
    int getCouponByOrder(String orderNo);

    /**
     * 通过用户编号查询是否领取新客大礼包
     * @param customerNo
     * @return
     */
    boolean getNewGiftBag(String customerNo);
}
