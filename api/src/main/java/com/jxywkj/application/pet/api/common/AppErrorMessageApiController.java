package com.jxywkj.application.pet.api.common;

import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.common.AppErrorMessage;
import com.jxywkj.application.pet.service.facade.common.AppErrorMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName AppErrorMessageApiController
 * @Description app错误日志
 * @Author LiuXiangLin
 * @Date 2019/9/30 10:17
 * @Version 1.0
 **/
@Api(description = "app错误日志收集")
@RestController
@RequestMapping("/api/app/error/message")
public class AppErrorMessageApiController {
    @Resource
    AppErrorMessageService appErrorMessageService;


    @ApiOperation(value = "提交数据")
    @PostMapping
    public JsonResult save(@RequestBody AppErrorMessage appErrorMessage) {
        return JsonResult.success(appErrorMessageService.save(appErrorMessage));
    }
}
