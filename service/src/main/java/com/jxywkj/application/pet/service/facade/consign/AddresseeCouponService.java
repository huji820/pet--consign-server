package com.jxywkj.application.pet.service.facade.consign;

/**
 * @ClassName AddresseeCouponService
 * @Description 优惠券
 * @Author LiuXiangLin
 * @Date 2019/7/19 16:41
 * @Version 1.0
 **/
public interface AddresseeCouponService {
    /**
     * @return void
     * @Author LiuXiangLin
     * @Description 赠送收件人一张优惠券
     * @Date 16:43 2019/7/19
     * @Param [phoneNumber]
     **/
    void sendAddresseeAnCoupon(String phoneNumber);

}
