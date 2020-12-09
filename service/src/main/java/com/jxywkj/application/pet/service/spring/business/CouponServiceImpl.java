package com.jxywkj.application.pet.service.spring.business;

import com.jxywkj.application.pet.common.enums.CouponStateEnum;
import com.jxywkj.application.pet.dao.business.CouponMapper;
import com.jxywkj.application.pet.model.consign.Coupon;
import com.jxywkj.application.pet.service.facade.business.CouponService;
import com.jxywkj.application.pet.service.facade.consign.CustomerCouponService;
import com.yangwang.sysframework.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.yangwang.sysframework.utils.DateUtil.FORMAT_FULL;

/**
 * @ClassName CouponService
 * @Description: 优惠券
 * @Author Aze
 * @Date 2019/8/14 17:31
 * @Version 1.0
 **/
@Service
public class CouponServiceImpl implements CouponService {
    @Autowired
    CouponMapper couponMapper;

    @Override
    public int verificationCoupon(String couponNo, String businessNo) {
        return couponMapper.verificationCoupon(getVerificationCoupon(couponNo, businessNo));
    }

    @Override
    public List<Coupon> listByCouponNo(String couponNo) {
        return couponMapper.listByCouponNo(couponNo);
    }

    /**
     * @return com.jxywkj.application.pet.model.consign.Coupon
     * @Author LiuXiangLin
     * @Description 获取核销券对象
     * @Date 17:45 2019/8/29
     * @Param [couponNo, businessNo]
     **/
    private Coupon getVerificationCoupon(String couponNo, String businessNo) {
        Coupon result = new Coupon();
        result.setCouponNo(couponNo);
        result.setBusinessNo(businessNo);
        result.setCouponState(CouponStateEnum.USED.getSate());
        result.setUseTime(DateUtil.format(new Date(), FORMAT_FULL));

        return result;
    }
}
