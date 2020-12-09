package com.jxywkj.application.pet.api.common;

import com.jxywkj.application.pet.common.enums.Code;
import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.common.Customer;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.service.facade.common.CustomerMessageService;
import com.jxywkj.application.pet.service.facade.common.CustomerService;
import com.jxywkj.application.pet.service.facade.consign.StationMessageService;
import com.jxywkj.application.pet.service.facade.consign.StationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName MessageApiController
 * @Description
 * @Author LiuXiangLin
 * @Date 2019/9/9 15:20
 * @Version 1.0
 **/
@Api(description = "站内消息")
@RestController
    @RequestMapping(value = "/api/message/push/")
public class CustomerPushApiController {

    @Resource
    CustomerService customerService;

    @Resource
    StationMessageService stationMessageService;

    @Resource
    StationService stationService;

    @Resource
    CustomerMessageService customerMessageService;

    @ApiOperation(value = "通openId查询所有的站点消息条数")
    @GetMapping("")
    public JsonResult listByCustomerId(String customerNo, String lastModifyTime) {
        // 获取
        Station station = stationService.getByCustomerNo(customerNo);

        // 如果站点信息不为空 则表示是管理员
        if (station != null) {
            return JsonResult.success(stationMessageService.countAllAdminUnreadByStationNo(station.getStationNo(), lastModifyTime));
        }

        // 获取customer对象
        Customer customer = customerService.getCustomerByCustomerNo(customerNo);
        if (customer == null) {
            return JsonResult.error(Code.CHECK_ERROR, "身份校验错误！");
        }

        return JsonResult.success(customerMessageService.countUnreadByPhone(customer.getPhone(), lastModifyTime));
    }
}
