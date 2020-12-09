package com.jxywkj.application.pet.api.consign;

import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.service.facade.consign.CustomerCouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName CouponAPIController
 * @Description 优惠券
 * @Author LiuXiangLin
 * @Date 2019/8/20 17:30
 * @Version 1.0
 **/

@Api(description = "优惠券")
@RestController
@RequestMapping("aip/coupon")
public class CouponApiController {

    @Resource
    CustomerCouponService customerCouponService;

    @ApiOperation(value = "通过openId查询数据")
    @GetMapping("/listByCustomerNo")
    public JsonResult getByOpnId(@RequestParam("customerNo") String customerNo) {
        return JsonResult.success(customerCouponService.listByCustomerNo(customerNo));
    }

    @ApiOperation(value = "通过customerNo查看用户是否领取新客大礼包 true 未领取  false 已领取")
    @GetMapping("/getNewGiftBag")
    public JsonResult getNewGiftBag(@RequestParam("customerNo")String customerNo){
        return JsonResult.success(customerCouponService.getNewGiftBag(customerNo));
    }
}
