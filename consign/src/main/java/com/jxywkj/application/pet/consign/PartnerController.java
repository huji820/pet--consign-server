package com.jxywkj.application.pet.consign;

import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.consign.Partner;
import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.service.facade.consign.PartnerService;
import com.yangwang.sysframework.utils.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @Description
 * @Author Administrator
        * @Date 2019-06-23 19:08
        * @Version 1.0
        */
@RestController
@RequestMapping("/consign/partner")
public class PartnerController {

    @Autowired
    PartnerService partnerService;

    @GetMapping("")
    public Pagination listPartner(int pageIndex, int pageSize, String partnerName, String province, String city, String county, @SessionAttribute("staff") Staff staff) {
        Pagination pagination = new Pagination(pageIndex-1, pageSize);
        pagination.setRoot(partnerService.listPartner(staff.getStation().getStationNo(), partnerName, province, city, county, pagination.getStart(), pageSize));
        pagination.setTotalCount(partnerService.countPartner(staff.getStation().getStationNo(), partnerName, province, city));
        return pagination;
    }

    @PostMapping("")
    public JsonResult insertPartner(@RequestBody Partner partner, @SessionAttribute("staff") Staff staff) {
        partner.setStation(staff.getStation());
        partnerService.insertPartner(partner);
        return JsonResult.success(partner);
    }

    @DeleteMapping("/{partnerNo:[\\d]+}")
    public JsonResult deletePartner(@PathVariable("partnerNo") int partnerNo) {
        partnerService.deletePartner(partnerNo);
        // 该网点下的运输线路也需要删除
        return JsonResult.success("删除合作伙伴成功");
    }

    @PutMapping("")
    public JsonResult updatePartner(@RequestBody Partner partner) {
        partnerService.updatePartner(partner);
        return JsonResult.success("删除合作伙伴成功");
    }
}
