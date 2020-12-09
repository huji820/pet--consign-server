package com.jxywkj.application.pet.api.consign;

import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.consign.OrderRemarks;
import com.jxywkj.application.pet.service.facade.consign.OrderRemarksService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName OrderRemarksApiController
 * @Description 订单备注
 * @Author LiuXiangLin
 * @Date 2019/9/23 11:25
 * @Version 1.0
 **/
@RestController
@Api(description = "订单备注")
@RequestMapping("/api/order/remarks")
public class OrderRemarksApiController {

    @Resource
    OrderRemarksService orderRemarksService;

    @PostMapping("")
    @ApiOperation(value = "添加一条备注")
    public JsonResult saveRemarks(@RequestBody OrderRemarks orderRemarks) {
        return JsonResult.success(orderRemarksService.saveRemarks(orderRemarks));
    }

    @DeleteMapping("/{remarksNo}")
    @ApiOperation(value = "删除数据")
    public JsonResult deleteByRemarksNo(@PathVariable int remarksNo) {
        return null;
    }

    @PutMapping("")
    @ApiOperation(value = "更新数据")
    public JsonResult update(OrderRemarks orderRemarks) {
        return null;
    }
}