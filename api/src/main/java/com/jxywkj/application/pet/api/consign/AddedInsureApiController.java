package com.jxywkj.application.pet.api.consign;

import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.consign.AddedInsure;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @ClassName AddedInsureController
 * @Author LiuXiangLin
 * @Date 2019/7/25 15:29
 * @Version 1.0
 **/


@RestController
@RequestMapping("/api/consign/insure")
@Api(description = "增值服务_保价")
public class AddedInsureApiController {

    /**
     * 修改了需求 所有站点的保价金额费率都为0.01
     */
    @ApiOperation("获取通过城市获取保价配置")
    @GetMapping("")
    public JsonResult getInsure(@RequestParam("startCity") String startCity) {
        return JsonResult.success(new AddedInsure(BigDecimal.valueOf(0.01)));
    }

}
