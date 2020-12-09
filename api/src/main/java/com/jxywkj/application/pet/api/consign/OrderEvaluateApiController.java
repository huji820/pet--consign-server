package com.jxywkj.application.pet.api.consign;

import com.jxywkj.application.pet.common.enums.Code;
import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.consign.ConsignOrderEvaluate;
import com.jxywkj.application.pet.service.facade.business.ConsignOrderEvaluateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 订单评价
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className OrderEvaluateApiController
 * @date 2019/12/11 17:17
 **/
@RestController
@RequestMapping("/api/order/evaluate")
@Api(description = "订单评价")
public class OrderEvaluateApiController {
    @Resource
    ConsignOrderEvaluateService consignOrderEvaluateService;

    @ApiOperation(value = "新增订单评价")
    @PostMapping()
    public JsonResult save(@RequestBody ConsignOrderEvaluate consignOrderEvaluate) {
        if (consignOrderEvaluate == null) {
            return JsonResult.error(Code.PARAM_ERROR, "参数为空");
        }
        return JsonResult.success(consignOrderEvaluateService.save(consignOrderEvaluate));
    }
}
