package com.jxywkj.application.pet.api.consign;

import com.jxywkj.application.pet.common.enums.Code;
import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.consign.Order;
import com.jxywkj.application.pet.model.consign.OrderTakeDetail;
import com.jxywkj.application.pet.service.facade.consign.ConsignOrderService;
import com.jxywkj.application.pet.service.facade.consign.OrderTakeDetailService;
import com.jxywkj.application.pet.service.facade.consign.TransportTakeDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 订单提取详情
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className OrderTakeDetailApiController
 * @date 2020/4/8 9:58
 **/
@RestController
@Api(description = "订单提取详情")
@RequestMapping(value = "/api/order/take-detail")
public class OrderTakeDetailApiController {
    @Resource
    OrderTakeDetailService orderTakeDetailService;

    @Resource
    ConsignOrderService consignOrderService;

    @Resource
    TransportTakeDetailService transportTakeDetailService;

    @PostMapping
    @ApiOperation(value = "新增订单提取详情")
    public JsonResult save(@RequestBody OrderTakeDetail orderTakeDetail) throws Exception {
        checkParam(orderTakeDetail);
        orderTakeDetailService.save(orderTakeDetail);
        return JsonResult.success();
    }

    @GetMapping("/default/{orderNo:\\w+}")
    @ApiOperation(value = "获取默认配置")
    public JsonResult getByTransportNo(@PathVariable(name = "orderNo") String orderNo) {
        // 获取订单
        Order order = consignOrderService.getConsignOrderByOrderNo(orderNo);
        if (order != null) {
            return JsonResult.success(transportTakeDetailService.listByTransportNoAndCode(order.getTransport().getTransportNo(), null));
        }
        return JsonResult.error(Code.CHECK_ERROR, "单据不存在！");
    }

    @GetMapping("/default/{orderNo:\\w+}/code/{code:\\w+}")
    @ApiOperation(value = "获取默认配置")
    public JsonResult getByTransportNo(@PathVariable(name = "orderNo") String orderNo,
                                       @PathVariable(required = false, name = "code") String code) {
        // 获取订单
        Order order = consignOrderService.getConsignOrderByOrderNo(orderNo);
        if (order != null) {
            return JsonResult.success(transportTakeDetailService.listByTransportNoAndCode(order.getTransport().getTransportNo(), code));
        }
        return JsonResult.error(Code.CHECK_ERROR, "单据不存在！");

    }


    /**
     * <p>
     * 校验参数
     * </p>
     *
     * @param orderTakeDetail 订单提取详情对象
     * @return void
     * @author LiuXiangLin
     * @date 10:07 2020/4/8
     **/
    private void checkParam(OrderTakeDetail orderTakeDetail) {
        if (orderTakeDetail.getOrder() == null
                || orderTakeDetail.getOrder().getOrderNo() == null
                || orderTakeDetail.getContact() == null
                || orderTakeDetail.getPhone() == null
                || orderTakeDetail.getProvince() == null
                || orderTakeDetail.getCity() == null
                || orderTakeDetail.getRegion() == null
                || orderTakeDetail.getDetailAddress() == null) {
            throw new RuntimeException("参数不全！");
        }

    }
}
