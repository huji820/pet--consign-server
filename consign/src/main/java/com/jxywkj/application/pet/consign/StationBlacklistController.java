package com.jxywkj.application.pet.consign;

import com.jxywkj.application.pet.common.enums.Code;
import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.model.consign.StationBlacklist;
import com.jxywkj.application.pet.service.facade.consign.StationBlackListService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 站点黑名单
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className StationBlacklistController
 * @date 2019/11/23 16:40
 **/
@RestController
@RequestMapping("/consign/blacklist")
public class StationBlacklistController {
    @Resource
    StationBlackListService stationBlackListService;

    @GetMapping("/all")
    public JsonResult listAll(@SessionAttribute("staff") Staff staff) {
        return JsonResult.success(stationBlackListService.listByStationNo(staff.getStation().getStationNo()));
    }

    @PostMapping()
    public JsonResult save(@RequestBody StationBlacklist stationBlacklist, @SessionAttribute("staff") Staff staff) {
        if (stationBlacklist == null) {
            return JsonResult.error(Code.PARAM_ERROR, "请求参数为空");
        }
        stationBlacklist.setStation(staff.getStation());
        return JsonResult.success(stationBlackListService.saveOrUpdate(stationBlacklist));
    }

    @DeleteMapping()
    public JsonResult delete(@RequestParam("stationNo") int stationNo, @SessionAttribute("staff") Staff staff) {
        return JsonResult.success(stationBlackListService.deleteByBlackStationNo(staff.getStation().getStationNo(), stationNo));
    }
}
