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
@RequestMapping(value = "/api/message/customer")
public class CustomerMessageApiController {

    @Resource
    CustomerService customerService;

    @Resource
    CustomerMessageService customerMessageService;

    @Resource
    StationMessageService stationMessageService;

    @Resource
    StationService stationService;


    @ApiOperation(value = "通openId查询所有的站点消息")
    @GetMapping("")
    public JsonResult listByCustomerId(@RequestParam("customerNo") String customerNo,
                                       @RequestParam("offset") int offset,
                                       @RequestParam("limit") int limit) {
        // 管理员
        Station station = stationService.getByCustomerNo(customerNo);
        if (station != null) {
            return JsonResult.success(stationMessageService.listAllAdminMsgByStationNo(station.getStationNo(), offset, limit));
        }

        // 用户或者员工
        Customer customer = customerService.getCustomerByCustomerNo(customerNo);
        if (customer == null) {
            return JsonResult.error(Code.CHECK_ERROR, "身份验证失败！");
        }

        return JsonResult.success(customerMessageService.listByPhone(customer.getPhone(), offset, limit));

    }

    @ApiOperation(value = "更新消息类型")
    @PutMapping()
    public JsonResult updateMessageType() {
        return null;
    }
}
