package com.jxywkj.application.pet.admin.station;

import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.service.facade.consign.StationService;
import com.yangwang.sysframework.utils.JsonResult;
import com.yangwang.sysframework.utils.JsonUtil;
import com.yangwang.sysframework.utils.Pagination;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author Administrator
 * @Date 2019-12-08 14:50
 * @Version 1.0
 */
@RestController("adminStationController")
@RequestMapping("admin/station/info")
public class StationController {

    @Autowired
    StationService stationService;

    @GetMapping("")
    public Pagination listStation(@RequestParam(value = "stationNo", required = false, defaultValue = "0") int stationNo,
                                  @RequestParam(value = "stationName", required = false, defaultValue = "") String stationName,
                                  @RequestParam(value = "province", required = false) String province, int start, int limit) {
        Pagination pagination = new Pagination();

        Station station = new Station();
        station.setStationNo(stationNo);
        station.setStationName(stationName);
        station.setProvince(province);

        pagination.setRoot(stationService.listAdminStation(station, start, limit));
        pagination.setTotalCount(stationService.countAdminStation(station));

        return pagination;
    }

    @DeleteMapping("/{detail}")
    public JsonResult listStation(@PathVariable("detail") String detail) throws Exception {
        List<Station> stations = (ArrayList<Station>) JsonUtil.formObject(detail);
        stationService.deleteStation(stations);
        if (stationService.deleteStation(stations) > 0) {
            return JsonResult.success("删除站点成功");
        } else {
            return JsonResult.fail(123213, "删除站点失败");
        }
    }

    @GetMapping("/uploadCollectionQRCode")
    public JsonResult uploadCollectionQRCode(@RequestParam("collectionQRCode")String collectionQRCode,
                                             @Param("stationNo")int stationNo){
        int i = stationService.uploadCollectionQRCode(collectionQRCode, stationNo);
        return JsonResult.success(i);
    }
}
