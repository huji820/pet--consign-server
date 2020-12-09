package com.jxywkj.application.pet.admin.station;

import com.jxywkj.application.pet.model.business.Business;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.service.facade.business.BusinessService;
import com.jxywkj.application.pet.service.facade.consign.StaffService;
import com.yangwang.sysframework.utils.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author Administrator
 * @Date 2019-12-08 14:50
 * @Version 1.0
 */
@RestController("adminStaffController")
@RequestMapping("admin/staff/info")
public class StaffController {

    @Autowired
    StaffService staffService;

    @GetMapping("")
    public Pagination listStation(@RequestParam(value = "stationNo", required = false, defaultValue = "0") int stationNo,
                                  @RequestParam(value = "stationName", required = false, defaultValue = "") String stationName,
                                    int start, int limit) {
        Pagination pagination = new Pagination();

        Station station = new Station();
        station.setStationNo(stationNo);
        station.setStationName(stationName);

        pagination.setRoot(staffService.listAdminStaff(stationNo, start, limit));
        pagination.setTotalCount(staffService.countAdminStaff(stationNo));

        return pagination;
    }

}
