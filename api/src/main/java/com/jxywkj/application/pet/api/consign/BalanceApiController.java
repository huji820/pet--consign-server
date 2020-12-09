package com.jxywkj.application.pet.api.consign;

import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.service.facade.consign.BalanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @ClassName BalanceAPIController
 * @Description 余额查询
 * @Author LiuXiangLin
 * @Date 2019/8/20 9:34
 * @Version 1.0
 **/
@Api(description = "余额")
@RestController
@RequestMapping("/api/balance")
public class BalanceApiController {

    @Resource
    BalanceService balanceService;

    @ApiOperation(value = "查询用户余额")
    @GetMapping("")
    public JsonResult getCustomerBalance(@RequestParam("customerNo") String customerNo) {
        return JsonResult.success(balanceService.getByCustomerNo(customerNo));
    }
}
