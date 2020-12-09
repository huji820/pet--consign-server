package com.jxywkj.application.pet.api.consign;

import com.jxywkj.application.pet.common.enums.Code;
import com.jxywkj.application.pet.common.enums.OrderStatusEnum;
import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.common.utils.StringUtils;
import com.jxywkj.application.pet.model.consign.OrderPremium;
import com.jxywkj.application.pet.service.facade.consign.OrderPremiumApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author LiuXiangLin
 * @version 1.0
 * @className OrderSpreadApiController
 * @description 订单补差价
 * @date 2019/10/10 14:41
 **/
@RestController
@RequestMapping("/api/order/premium")
@Api(description = "订单补差价")
public class OrderPremiumApiController {
    @Resource
    OrderPremiumApiService orderPremiumApiService;

    @ApiOperation("新增差价单")
    @PostMapping()
    public JsonResult save(@RequestBody OrderPremium orderPremium) {
        return JsonResult.success(orderPremiumApiService.save(orderPremium));
    }

    @GetMapping("/count/unpaid")
    @ApiOperation("获取未支付的差价单数量（大于0为有）")
    public JsonResult countUnpaidOrders(@RequestParam("orderNo") String orderNo) {
        if (StringUtils.isBlank(orderNo)) {
            return JsonResult.error(Code.PARAM_ERROR, "参数为空");
        }
        return JsonResult.success(orderPremiumApiService.countByOrderNoAndType(orderNo, OrderStatusEnum.TO_BE_PAID.getState()));
    }

    @PutMapping("/cancel")
    @ApiOperation(value = "取消补价单")
    public JsonResult cancelBill(@RequestBody String billNo) {
        if (StringUtils.isBlank(billNo)) {
            return JsonResult.error(Code.CHECK_ERROR, "请求参数为空");
        }

        // 获取订单
        OrderPremium orderPremium = orderPremiumApiService.getByBillNo(billNo);
        if (orderPremium != null && OrderStatusEnum.TO_BE_PAID.getState().equals(orderPremium.getState())) {
            // 取消订单
           return JsonResult.success(orderPremiumApiService.cancelBill(billNo));
        }

        return JsonResult.error(Code.CHECK_ERROR, "补价单已完成或者已失效。");
    }
}
