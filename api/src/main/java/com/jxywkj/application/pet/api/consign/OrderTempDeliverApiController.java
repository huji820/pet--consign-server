package com.jxywkj.application.pet.api.consign;

import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.consign.OrderTempDeliver;
import com.jxywkj.application.pet.service.facade.consign.OrderTempDeliverService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName OrderTempDeliver
 * @Description 订单临时配送
 * @Author LiuXiangLin
 * @Date 2019/9/25 10:21
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/order/deliver")
@Api(description = "订单临时配送")
public class OrderTempDeliverApiController {
    @Resource
    OrderTempDeliverService orderTempDeliverService;

    @PostMapping()
    @ApiOperation(value = "添加一条临时配送")
    public JsonResult save(@RequestBody OrderTempDeliver orderTempDeliver) throws Exception {
        return JsonResult.success(orderTempDeliverService.save(orderTempDeliver));
    }
}
