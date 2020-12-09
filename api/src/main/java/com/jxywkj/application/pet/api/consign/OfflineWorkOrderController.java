package com.jxywkj.application.pet.api.consign;


import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.consign.params.OfflineWorkOrderDTO;
import com.jxywkj.application.pet.service.facade.consign.OfflineWorkOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 线下生成工单
 * @Author GuoPengCheng
 * @Date 2020-07-17 11:16
 * @Version 1.0
 */
@Api(description = "站点生成工单")
@RestController
@RequestMapping("/api/offlineOrder")
public class OfflineWorkOrderController {

    @Autowired
    OfflineWorkOrderService offlineWorkOrderService;

    @ApiOperation(value = "线下生成工单")
    @PostMapping("/insertOfflineWorkOrder")
    public JsonResult insertOfflineWorkOrder(@RequestBody OfflineWorkOrderDTO offlineWorkOrderDTO)throws Exception{
        return JsonResult.success(offlineWorkOrderService.insertOfflineWorkOrder(offlineWorkOrderDTO));
    }
    @ApiOperation(value = "通过工单编号查询订单金额")
    @GetMapping("/getOfflineWorkOrderPrice")
    public JsonResult getOfflineWorkOrderPrice(@RequestParam("orderNo") String orderNo)throws Exception{
        return JsonResult.success(offlineWorkOrderService.getOfflineWorkOrderPrice(orderNo));
    }
}
