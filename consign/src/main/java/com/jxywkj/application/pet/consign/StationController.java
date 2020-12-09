package com.jxywkj.application.pet.consign;

import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.service.facade.consign.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 站点
 *
 * @Description
 * @Author Administrator
 * @Date 2019-06-23 18:30
 * @Version 1.0
 */
@RestController
@RequestMapping("/consign/station")
public class StationController {

    @Autowired
    StationService stationService;

    @PutMapping("")
    public JsonResult updateStation(@RequestBody Station station, @SessionAttribute("staff") Staff staff) {
        station.setStationNo(staff.getStation().getStationNo());
        stationService.updateStation(station);
        return JsonResult.success("修改成功");
    }

    @GetMapping("my")
    public JsonResult myStation(@SessionAttribute("staff") Staff staff) {
        Station station = stationService.getStation(staff.getStation().getStationNo());
        return JsonResult.success(station);
    }

    @GetMapping("/all")
    public JsonResult listAllStation() {
        return JsonResult.success(stationService.listAllStation());
    }
}
