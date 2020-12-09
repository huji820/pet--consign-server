package com.jxywkj.application.pet.api.consign;

import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.consign.OrderTransport;
import com.jxywkj.application.pet.service.facade.consign.OrderTransportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName OrderTransportApiController
 * @Description
 * @Author LiuXiangLin
 * @Date 2019/9/23 14:51
 * @Version 1.0
 **/
@Api(description = "订单车次号")
@RestController
@RequestMapping("/api/order/transport")
public class OrderTransportApiController {

    @Resource
    OrderTransportService orderTransportService;

    @PostMapping()
    @ApiOperation(value = "添加航班信息")
    public JsonResult saveOrderTransport(@RequestBody OrderTransport orderTransport) {
        orderTransportService.saveOrderTransport(orderTransport);
        return JsonResult.success(1);
    }

}
