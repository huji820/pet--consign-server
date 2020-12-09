package com.jxywkj.application.pet.business;

import com.jxywkj.application.pet.common.enums.Code;
import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.business.Business;
import com.jxywkj.application.pet.service.facade.business.CouponService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName BusinessLoginController
 * @Description:优惠券
 * @Author Aze
 * @Date 2019/8/14 11:48
 * @Version 1.0
 **/
@RestController
@RequestMapping("/business/coupon")
public class CouponController {
    @Resource
    CouponService couponService;

    /**
     * @return
     * @Author Aze
     * @Description: 核销账单
     * @Date:
     * @Param
     */
    @PutMapping("/verificationCoupon/{couponNo}")
    public JsonResult cancelCoupon(@PathVariable String couponNo, HttpServletRequest httpServletRequest) {
        Business business = (Business) httpServletRequest.getSession().getAttribute("business");
        if (business == null) {
            return JsonResult.error(Code.CHECK_ERROR, "请重新登录");
        }
        return JsonResult.success(couponService.verificationCoupon(couponNo, business.getBusinessNo()));
    }

    @GetMapping("/listByCouponNo")
    public JsonResult listByCouponNo(@RequestParam("couponNo") String couponNo) {
        return JsonResult.success(couponService.listByCouponNo(couponNo));
    }

}
