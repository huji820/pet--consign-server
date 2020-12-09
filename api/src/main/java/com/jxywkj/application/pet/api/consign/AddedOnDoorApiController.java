package com.jxywkj.application.pet.api.consign;

import com.jxywkj.application.pet.common.utils.CollectionUtils;
import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.service.facade.consign.AddedReceiptService;
import com.jxywkj.application.pet.service.facade.consign.AddedSendService;
import com.jxywkj.application.pet.service.facade.consign.StationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName AddedOnDoorAPIController
 * @Description 上门接宠
 * @Author LiuXiangLin
 * @Date 2019/8/7 11:21
 * @Version 1.0
 **/
@Api(description = "增值服务_送宠上门")
@RestController
@RequestMapping("/api/consign/onDoorService")
public class AddedOnDoorApiController {
    @Resource
    StationService stationService;

    @Resource
    AddedSendService addedSendService;

    @Resource
    AddedReceiptService addedReceiptService;


    @GetMapping("/get/send")
    @ApiOperation(value = "获取送宠上门的配置")
    public JsonResult getSenderConfig(@RequestParam("cityName") String cityName) {
        // 获取站点信息
        List<Station> stations = stationService.listStation(cityName);
        if (!CollectionUtils.isEmpty(stations)) {
//            return JsonResult.success(addedSendService.getByStationNo(stations.get(0).getStationNo()));
        }
        return JsonResult.success();
    }

    @GetMapping("/get/receipt")
    @ApiOperation(value = "获取上门接宠配置")
    public JsonResult getReceipt(@RequestParam("cityName") String cityName) {
        // 获取站点信息
        List<Station> stations = stationService.listStation(cityName);
        if (!CollectionUtils.isEmpty(stations)) {
//            return JsonResult.success(addedReceiptService.getByStationNO(stations.get(0).getStationNo()));
        }
        return JsonResult.success();
    }
}
