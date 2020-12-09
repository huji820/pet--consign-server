package com.jxywkj.application.pet.api.consign;

import com.jxywkj.application.pet.common.enums.Code;
import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.service.facade.consign.ConsignOrderService;
import com.jxywkj.application.pet.service.facade.consign.StaffService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 运输订单编号
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className OrderNoApiController
 * @date 2020/3/12 15:36
 **/
@RestController
@RequestMapping("/api/consign/order-no")
@Api(description = "运输订单编号")
public class OrderNoApiController {
    @Resource
    ConsignOrderService consignOrderService;

    @Resource
    StaffService staffService;


    @GetMapping("/customer")
    @ApiOperation(value = "通过用户编号冲模糊查询单号")
    public JsonResult getOrderNoByCustomer(@RequestParam("orderNo") String orderNo, @RequestParam("customerNo") String customerNo) {
        return JsonResult.success(consignOrderService.getCustomerOrderNoByOrderNo(orderNo, customerNo));
    }

    @GetMapping("/staff")
    @ApiOperation(value = "通过员工编号模糊查询单号")
    public JsonResult getOrderNoByStaff(@RequestParam("orderNo") String orderNo, @RequestParam("staffNo") String staffNo) {
        Staff staff = staffService.getByStaffNo(Integer.valueOf(staffNo));
        if (staff == null) {
            return JsonResult.error(Code.NOT_EXISTS, "该员工不存在");
        }
        return JsonResult.success(consignOrderService.getStaffOrderNoByOrderNo(orderNo, staff));


    }

    @GetMapping("/auto")
    @ApiOperation(value = "通过员工编号以及订单编号查询符合要求的单号")
    public JsonResult getOrderByAuto(@RequestParam("orderNo") String orderNo, @RequestParam("customerNo") String customerNo) {
        return JsonResult.success(consignOrderService.getOrderByOrder(orderNo, customerNo));
    }

}
