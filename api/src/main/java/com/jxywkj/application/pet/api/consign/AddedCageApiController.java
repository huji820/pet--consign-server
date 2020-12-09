package com.jxywkj.application.pet.api.consign;

import com.jxywkj.application.pet.common.enums.Code;
import com.jxywkj.application.pet.common.utils.CollectionUtils;
import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.consign.AddedVolumeCage;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.model.consign.Transport;
import com.jxywkj.application.pet.service.facade.consign.AddedVolumeCageService;
import com.jxywkj.application.pet.service.facade.consign.StationService;
import com.jxywkj.application.pet.service.facade.consign.TransportService;
import io.swagger.annotations.Api;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 笼具接口
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className AddedCageApiController
 * @date 2019/10/19 14:53
 **/
@Api(description = "笼具配置")
@RestController
@RequestMapping("/api/consign/cage")
public class AddedCageApiController {
    @Resource
    TransportService transportService;

    @Resource
    StationService stationService;

    @Resource
    AddedVolumeCageService addedVolumeCageService;

    /**
     * 获取站点运输的最大值
     *
     * @param startCity     开始城市
     * @param endCity       终点城市
     * @param transportType 运输类型
     * @return com.jxywkj.application.pet.common.utils.JsonResult
     * @author LiuXiangLin
     * @date 15:16 2019/10/19
     **/
    @GetMapping("/max")
    public JsonResult getMaxWeight(@Param("startCity") String startCity,
                                   @Param("endCity") String endCity,
                                   @Param("transportType") Integer transportType) {

        // 获取运输路线
        List<Transport> transportList = transportService.listByCityAndType(startCity, endCity, transportType);
        if (CollectionUtils.isEmpty(transportList)) {
            return JsonResult.error(Code.CHECK_ERROR, "无法获取所在运输路线");
        }
        return JsonResult.success(transportList.get(0).getMaxWeight());
    }

    @GetMapping("/exists")
    public JsonResult isExists(@RequestParam("startCity") String startCity,
                               @RequestParam("endCity") String endCity,
                               @RequestParam("transportType") Integer transportType) {
        // 获取运输路线
        List<Transport> transportList = transportService.listByCityAndType(startCity, endCity, transportType);
        List<AddedVolumeCage> addedVolumeCages = null;
        //获取城市下所有站点
        List<Station> stations = stationService.listStation(startCity);
        if(stations!=null){
            if (CollectionUtils.isNotEmpty(transportList)) {
                // 获取笼具配置
                addedVolumeCages = addedVolumeCageService.listByTransportNo(transportList.get(0).getTransportNo(),stations.get(0).getStationNo());
            }
        }

        return JsonResult.success(CollectionUtils.isNotEmpty(addedVolumeCages) ? true : false);
    }
}
