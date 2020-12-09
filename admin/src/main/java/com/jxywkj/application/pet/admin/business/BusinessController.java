package com.jxywkj.application.pet.admin.business;

import com.jxywkj.application.pet.common.enums.BusinessStateEnum;
import com.jxywkj.application.pet.model.business.Business;
import com.jxywkj.application.pet.service.facade.business.BusinessService;
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
@RestController("adminBusinessController")
@RequestMapping("admin/busioness/info")
public class BusinessController {

    @Autowired
    BusinessService businessService;

    @GetMapping("")
    public Pagination listBusiness(@RequestParam(value = "businessNo", required = false, defaultValue = "") String businessNo,
                                   @RequestParam(value = "businessName", required = false, defaultValue = "") String businessName,
                                   @RequestParam(value = "province", required = false) String province,
                                   @RequestParam(value = "city", required = false) String city,
                                   @RequestParam(value = "district", required = false) String district,
                                   @RequestParam(value = "startDate", required = false) String startDate,
                                   @RequestParam(value = "endDate", required = false) String endDate,
                                   int start, int limit) {
        Pagination pagination = new Pagination();

        Business business = new Business();
        business.setBusinessNo(businessNo);
        business.setBusinessName(businessName);
        business.setProvince(province);
        business.setCity(city);
        business.setArea(district);

        pagination.setRoot(businessService.listAdminBusiness(business, startDate, endDate, start, limit, BusinessStateEnum.NORMAL));
        pagination.setTotalCount(businessService.countAdminBusiness(business, BusinessStateEnum.NORMAL));

        return pagination;
    }
}
