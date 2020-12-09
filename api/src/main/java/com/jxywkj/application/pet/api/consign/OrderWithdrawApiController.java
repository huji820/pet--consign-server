package com.jxywkj.application.pet.api.consign;

import com.jxywkj.application.pet.common.enums.Code;
import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.common.utils.StringUtils;
import com.jxywkj.application.pet.service.facade.business.BusinessWithdrawService;
import com.jxywkj.application.pet.service.facade.consign.StationWithdrawService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @ClassName OrderWithdrawApiController
 * @Description 提现
 * @Author LiuXiangLin
 * @Date 2019/8/26 17:56
 * @Version 1.0
 **/
@Api(description = "提现")
@RestController
@RequestMapping("/api/withdraw")
public class OrderWithdrawApiController {
    @Resource
    StationWithdrawService stationWithdrawService;

    @Resource
    BusinessWithdrawService businessWithdrawService;


    @ApiOperation("站点提现")
    @PostMapping("/station")
    public JsonResult withdraw(@RequestParam("customerNo") String customerNo, @RequestParam("amount") BigDecimal amount) {
        return JsonResult.success(stationWithdrawService.saveStationWithdraw(customerNo, amount));
    }

    @ApiOperation(value = "商家提现")
    @PostMapping("/business")
    public JsonResult businessWithdraw(@RequestParam("businessNo") String businessNo, @RequestParam("amount") BigDecimal amount) {
        return JsonResult.success(businessWithdrawService.saveBusinessWithdraw(businessNo, amount));
    }

    @ApiOperation(value = "站点提现流水")
    @GetMapping("/station/flow")
    public JsonResult stationWithdrawFlow(@RequestParam("stationNo") String stationNo,
                                          @RequestParam("offset") int offset,
                                          @RequestParam("limit") int limit) {
        if (StringUtils.isBlank(stationNo)) {
            return JsonResult.error(Code.PARAM_ERROR, "参数为空");
        }
        return JsonResult.success(stationWithdrawService.listByStationNo(stationNo, offset, limit));
    }

    @ApiOperation(value = "商家提现流水")
    @GetMapping("/business/flow")
    public JsonResult businessWithdrawFlow(@RequestParam("businessNo") String businessNo,
                                           @RequestParam("offset") int offset,
                                           @RequestParam("limit") int limit) {
        if (StringUtils.isBlank(businessNo)) {
            return JsonResult.error(Code.PARAM_ERROR, "请求参数为空");
        }
        return JsonResult.success(businessWithdrawService.listByStationNo(businessNo, offset, limit));
    }
}



