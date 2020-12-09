package com.jxywkj.application.pet.api.consign;

import com.jxywkj.application.pet.common.enums.Code;
import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.common.utils.StringUtils;
import com.jxywkj.application.pet.service.facade.consign.ConsignOrderService;
import com.jxywkj.application.pet.service.facade.consign.PortService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName ProtApiController
 * @Descriptionc 出港入港
 * @Author LiuXiangLin
 * @Date 2019/8/9 14:03
 * @Version 1.0
 **/
@Api(description = "出港入港")
@RestController
@RequestMapping("/api/consign/port")
public class PortApiController {
    @Resource
    ConsignOrderService consignOrderService;

    @Resource
    PortService portService;

    @ApiOperation("通过单号模糊查询出入港单(查询工单)")
    @GetMapping("/listByLikeOrderNo")
    public JsonResult listByLikeOrderNo(String queryParamStr) throws Exception {
        if (StringUtils.isBlank(queryParamStr)) {
            return JsonResult.error(Code.PARAM_ERROR, "请求参数为空！");
        }
        return JsonResult.success(portService.listStaffOrderListByParam(queryParamStr));
    }


    @ApiOperation("获取这个站点所有的订单（所有单据）")
    @GetMapping("/list/Complete")
    public JsonResult listCompletedOrder(String queryParamStr) throws Exception {
        return JsonResult.success(consignOrderService.listCompleteOrder(queryParamStr));
    }
}
