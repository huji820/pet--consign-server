package com.jxywkj.application.pet.api.common;

import com.jxywkj.application.pet.common.enums.Code;
import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.common.utils.RequestUtils;
import com.jxywkj.application.pet.service.facade.consign.ConsignOrderService;
import com.jxywkj.application.pet.service.facade.consign.OrderPremiumApiService;
import com.jxywkj.application.pet.service.facade.consign.RechargeOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @ClassName WeChatAPIPay
 * @Description 微信支付路径
 * @Author LiuXiangLin
 * @Date 2019/8/19 9:26
 * @Version 1.0
 **/
@Api(description = "微信支付参数")
@RestController
@RequestMapping("/api/weChat/pay")
public class WeChatApiPay {
    @Resource
    ConsignOrderService consignOrderService;

    @Resource
    RechargeOrderService rechargeOrderService;

    @Resource
    OrderPremiumApiService orderPremiumApiService;

    @ApiOperation("获取订单代支付参数")
    @GetMapping("/getOtherOrderPayParam")
    public JsonResult getOtherOrderPayParam(@RequestParam("customerNo") String customerNo,
                                       @RequestParam("orderNo") String orderNo,
                                       @RequestParam("appType") String appType,
                                       HttpServletRequest request) throws Exception {
        Map<String, String> resultMap = consignOrderService.getOtherWeChatPayParam(customerNo, orderNo, appType, RequestUtils.getIpAddress(request));

        return resultMap == null ? JsonResult.error(Code.ERROR, "获取代支付参数失败！") : JsonResult.success(resultMap);
    }

    @ApiOperation("获取订单支付参数")
    @GetMapping("/getOrderPayParam")
    public JsonResult getOrderPayParam(@RequestParam("customerNo") String customerNo,
                                       @RequestParam("orderNo") String orderNo,
                                       @RequestParam("appType") String appType,
                                       HttpServletRequest request) throws Exception {
        Map<String, String> resultMap = consignOrderService.getWeChatPayParam(customerNo, orderNo, appType, RequestUtils.getIpAddress(request));

        return resultMap == null ? JsonResult.error(Code.ERROR, "获取支付参数失败！") : JsonResult.success(resultMap);
    }

    @ApiOperation("获取充值参数")
    @GetMapping("/getRechargeParam")
    public JsonResult getRechargeParam(@RequestParam("customerNo") String customerNo,
                                       @RequestParam("rechargeAmount") BigDecimal rechargeAmount,
                                       @RequestParam("appType") String appType,
                                       HttpServletRequest request) throws Exception {
        Map<String, String> resultMap = rechargeOrderService.addRechargeOrder(customerNo, rechargeAmount, appType, RequestUtils.getIpAddress(request));
        return resultMap == null ? JsonResult.error(Code.ERROR, "获取支付参数失败！") : JsonResult.success(resultMap);
    }

    @ApiOperation("获取补价支付参数")
    @GetMapping("/getOrderPremiumParam")
    public JsonResult getOrderPremiumParam(@RequestParam("billNo") String billNo,
                                           @RequestParam("customerNo") String customerNo,
                                           @RequestParam("appType") String appType,
                                           HttpServletRequest request) throws Exception {
        Map<String, String> resultMap = orderPremiumApiService.getOrderPayParam(billNo, customerNo, appType, RequestUtils.getIpAddress(request));
        return resultMap == null ? JsonResult.error(Code.ERROR, "获取支付参数失败！") : JsonResult.success(resultMap);
    }
}
