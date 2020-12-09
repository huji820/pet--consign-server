package com.jxywkj.application.pet.api.consign;

import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.service.facade.consign.OrderFlowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 订单流水
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className OrderFlowApiController
 * @date 2020/5/19 16:25
 **/
@RestController
@Api(description = "订单流水")
@RequestMapping("/api/order/flow")
public class OrderFlowApiController {
    @Resource
    OrderFlowService orderFlowService;

    @GetMapping("/station/{stationNo:\\w+}/offset/{offset:\\w+}/limit/{limit:\\w+}")
    @ApiOperation(value = "站点订单流水")
    public JsonResult listStationFlow(@PathVariable(name = "stationNo") int stationNo,
                                      @PathVariable(name = "offset") int offset,
                                      @PathVariable(name = "limit") int limit) {
        return JsonResult.success(orderFlowService.listByStationNo(stationNo, offset, limit));
    }

    @GetMapping("/business/{businessNo:\\w+}/offset/{offset:\\w+}/limit/{limit:\\w+}")
    @ApiOperation(value = "商家订单流水")
    public JsonResult listBusinessFlow(@PathVariable(name = "businessNo") String businessNo,
                                       @PathVariable(name = "offset") int offset,
                                       @PathVariable(name = "limit") int limit) {
        return JsonResult.success(orderFlowService.listByBusinessNo(businessNo, offset, limit));
    }

    @GetMapping("/getByOrderNoAndStationNo")
    @ApiOperation("通过订单编号和站点编号获取流水")
    public JsonResult getByOrderNoAndStationNo(@RequestParam("orderNo")String orderNo,
                                               @RequestParam("stationNo")String stationNo){
        return  JsonResult.success(orderFlowService.getByOrderNoAndStationNo(orderNo, stationNo));
    }
}
