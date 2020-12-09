package com.jxywkj.application.pet.consign;

import com.jxywkj.application.pet.common.enums.Code;
import com.jxywkj.application.pet.common.utils.CollectionUtils;
import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.consign.AddedWeightCage;
import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.model.consign.dto.AddedVolumeCageDTO;
import com.jxywkj.application.pet.service.facade.consign.AddedVolumeCageService;
import com.jxywkj.application.pet.service.facade.consign.AddedWeightCageService;
import com.jxywkj.application.pet.service.facade.consign.StationService;
import com.jxywkj.application.pet.service.facade.consign.TransportService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 笼具配置
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className AddedCageController
 * @date 2019/10/19 14:45
 **/
@RestController()
@RequestMapping("/consign/cage")
public class AddedCageController {
    @Resource
    AddedWeightCageService addedWeightCageService;

    @Resource
    AddedVolumeCageService addedVolumeCageService;

    @Resource
    TransportService transportService;

    @Resource
    StationService stationService;

    /**
     * 更新一个站点的一个梯度的笼具配置
     *
     * @param addedWeightCageList 笼具梯度
     * @param staff               员工对象
     * @return com.jxywkj.application.pet.common.utils.JsonResult
     * @author LiuXiangLin
     * @date 14:51 2019/10/19
     **/
    @PutMapping("/weight")
    public JsonResult savOrUpdateWeightCage(@RequestBody List<AddedWeightCage> addedWeightCageList, @SessionAttribute("staff") Staff staff) {
        if (CollectionUtils.isEmpty(addedWeightCageList) || staff == null || staff.getStation() == null) {
            return JsonResult.error(Code.PARAM_ERROR, "请求参数为空");
        }
        return JsonResult.success(addedWeightCageService.saveOrUpdateList(addedWeightCageList, staff.getStation().getStationNo()));
    }

    @PutMapping("/volume")
    public JsonResult saveOrUpdate(@RequestBody List<AddedVolumeCageDTO> addedVolumeCageDtoList) {
        if (CollectionUtils.isEmpty(addedVolumeCageDtoList)) {
            return JsonResult.error(Code.PARAM_ERROR, "请求参参数为空");
        }
        return JsonResult.success(addedVolumeCageService.saveOrUpdate(addedVolumeCageDtoList));
    }

    @GetMapping()
    public JsonResult getByTransportType(@RequestParam int transportType, @SessionAttribute("staff") Staff staff) {
        return JsonResult.success(addedWeightCageService.listByStationNoAndTransportType(staff.getStation().getStationNo(), transportType));
    }

    @GetMapping("/transport")
    public JsonResult getByTransportNo(@RequestParam("transportNo") int transportNo) {
        //通过开始城市获取站点
        List<Station> stations = stationService.getByTransportNo(transportNo);
        if(stations!=null){
            return JsonResult.success(addedVolumeCageService.listByTransportNo(transportNo,stations.get(0).getStationNo()));
        }
        return JsonResult.success(null);
    }
}
