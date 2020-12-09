package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.Coupon;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName CustomerCouponMapper
 * @Description 用户优惠券
 * @Author LiuXiangLin
 * @Date 2019/8/20 13:55
 * @Version 1.0
 **/
@Component
public interface CustomerCouponMapper {

    /**
     * @Author LiuXiangLin
     * @Description 新增一张优惠券
     * @Date 13:56 2019/8/20
     * @Param [coupon]
     * @return int
     **/
    int addCustomerCoupon(@Param("coupon") Coupon coupon);

    /**
     * @Author LiuXiangLin
     * @Description 通过电话号码查询所有的券
     * @Date 17:22 2019/8/20
     * @Param [phone]
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Coupon>
     **/
    List<Coupon> listByPhone(@Param("phone") String phone);


    /**
     * @Author LiuXiangLin
     * @Description 通过客户主键查询数据
     * @Date 17:26 2019/8/20
     * @Param [customerNo]
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Coupon>
     **/
    List<Coupon> listByCustomerNo(@Param("customerNo") String customerNo);
}
