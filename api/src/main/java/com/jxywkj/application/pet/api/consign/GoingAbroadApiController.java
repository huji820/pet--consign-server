package com.jxywkj.application.pet.api.consign;

import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.consign.params.OfflineWorkOrderDTO;
import com.jxywkj.application.pet.service.facade.consign.GoingAbroadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 出国板块接口
 *
 * @version 1.0
 * @className GoingAbroadApiController
 * @date 2020/7/23 10:57
 **/
@Api(description = "出国板块")
@RestController
@RequestMapping("/api/consign/goingAbroad")
public class GoingAbroadApiController {
    @Resource
    GoingAbroadService goingAbroadService;

    @ApiOperation(value = "生成出国订单")
    @PostMapping("/insertOrder")
    public JsonResult insertOrder(@RequestBody OfflineWorkOrderDTO offlineWorkOrderDTO)throws Exception{
        return JsonResult.success(goingAbroadService.insertOrder(offlineWorkOrderDTO));
    }
}
