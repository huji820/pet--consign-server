package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.common.enums.CouponStateEnum;
import com.jxywkj.application.pet.common.utils.StringUtils;
import com.jxywkj.application.pet.model.consign.Coupon;
import com.jxywkj.application.pet.service.facade.consign.AddresseeCouponService;
import com.jxywkj.application.pet.service.facade.consign.CustomerCouponService;
import com.yangwang.sysframework.utils.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @ClassName AddresseeCouponServiceImpl
 * @Description 发券
 * @Author LiuXiangLin
 * @Date 2019/7/19 16:42
 * @Version 1.0
 **/
@Service
public class AddresseeCouponServiceImpl implements AddresseeCouponService {
    @Resource
    CustomerCouponService customerCouponService;

    @Override
    public void sendAddresseeAnCoupon(String phoneNumber) {
        Coupon coupon = new Coupon();
        coupon.setCouponNo(StringUtils.getUuid());
        coupon.setCouponName("优惠券");
        coupon.setCouponType("默认优惠券");
        coupon.setCouponDescribe("寄宠福利优惠券");
        coupon.setReleaseTime(DateUtil.format(new Date(), DateUtil.FORMAT_FULL));
        coupon.setFailureTime("9999-12-31 23:59:59");
        coupon.setPhone(phoneNumber);
        coupon.setCouponState(CouponStateEnum.available.getSate());

        customerCouponService.addCoupon(coupon);
    }
}
