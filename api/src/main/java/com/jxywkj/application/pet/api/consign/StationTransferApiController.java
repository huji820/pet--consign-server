package com.jxywkj.application.pet.api.consign;

import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.consign.StationTransfer;
import com.jxywkj.application.pet.service.facade.consign.BalanceService;
import com.jxywkj.application.pet.service.facade.consign.ConsignStationTransferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @ClassName StationTransferApiController
 * @Description 中转站点
 * @Author LiuXiangLin
 * @Date 2019/8/20 9:34
 * @Version 1.0
 **/
@Api(description = "中转站点")
@RestController
@RequestMapping("/api/stationTransfer")
public class StationTransferApiController {

    @Resource
    ConsignStationTransferService consignStationTransferService;

    @ApiOperation("添加或修改中转站点")
    @PostMapping("/insertOrUpdateStationTransfer")
    public JsonResult insertOrUpdateStationTransfer(@RequestParam("orderNo")String orderNo,
                                                    @RequestParam("stationNo")String stationNo){
        return JsonResult.success(consignStationTransferService.insertOrUpdateStationTransfer(orderNo,stationNo));
    }
}
