package com.jxywkj.application.pet.api.business;

import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.service.facade.business.BusinessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 商家列表
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className BusinessListApiController
 * @date 2020/4/23 14:13
 **/
@RestController
@Api(description = "商家列表")
@RequestMapping("/api/business/list")
public class BusinessListApiController {
    @Resource
    BusinessService businessService;

    @GetMapping("/city/group")
    @ApiOperation(value = "查询通过城市分组的商家数据")
    public JsonResult listGroupByCity() {
        return JsonResult.success(businessService.listByGroupCity());
    }

    @GetMapping("/province/{province:\\S+}")
    @ApiOperation(value = "通过省获取该省下的所有商家")
    public JsonResult listByProvince(@PathVariable("province") String province) {
        return JsonResult.success(businessService.listByProvince(province));
    }
}
