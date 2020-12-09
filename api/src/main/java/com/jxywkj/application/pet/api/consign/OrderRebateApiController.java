package com.jxywkj.application.pet.api.consign;

import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.service.facade.business.BusinessBalanceFlowService;
import com.jxywkj.application.pet.service.facade.consign.StationBalanceFlowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className OrderRebateApiController
 * @date 2019/12/6 10:33
 **/
@RestController
@RequestMapping("/api/rebate")
@Api(description = "返利")
public class OrderRebateApiController {
    @Resource
    StationBalanceFlowService stationBalanceFlowService;

    @Resource
    BusinessBalanceFlowService businessBalanceFlowService;


    @ApiOperation(value = "站点返利流水")
    @GetMapping("/station/flow")
    public JsonResult stationRebateFlow(@RequestParam("stationNo") int stationNo,
                                        @RequestParam("offset") int offset,
                                        @RequestParam("limit") int limit) {
        return JsonResult.success(stationBalanceFlowService.listStationFlow(new Station(stationNo), null, null, offset, limit));
    }

    @GetMapping("/business/flow")
    @ApiOperation(value = "商家返利流水")
    public JsonResult businessRebateFlow(@RequestParam("businessNo") String businessNo,
                                         @RequestParam("offset") int offset,
                                         @RequestParam("limit") int limit) {
        return JsonResult.success(businessBalanceFlowService.listByBusiness(businessNo, offset, limit));
    }

}
