package com.jxywkj.application.pet.api.consign;

import com.jxywkj.application.pet.common.enums.Code;
import com.jxywkj.application.pet.common.enums.StaffStateEnum;
import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.model.consign.params.OrderAssignmentParam;
import com.jxywkj.application.pet.service.facade.consign.OrderAssignmentService;
import com.jxywkj.application.pet.service.facade.consign.StaffService;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName OrderAssignmentApiController
 * @Description
 * @Author LiuXiangLin
 * @Date 2019/9/17 15:38
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/order/assignment")
@Api(description = "订单分配")
public class OrderAssignmentApiController {

    @Resource
    OrderAssignmentService orderAssignmentService;

    @Resource
    StaffService staffService;

    @PostMapping("/")
    public JsonResult assignmentOrder(@RequestBody OrderAssignmentParam orderAssignmentParam) {
        // 参数校验
        if (orderAssignmentParam == null
                || StringUtils.isBlank(orderAssignmentParam.getCustomerNo())
                || StringUtils.isBlank(orderAssignmentParam.getOrderNo())
                || CollectionUtils.isEmpty(orderAssignmentParam.getStaffNoList())) {
            return JsonResult.error(Code.CHECK_ERROR, "参数缺失！");
        }

        Staff staff = staffService.getStaffByCustomerNoAndStatus(orderAssignmentParam.getCustomerNo(), StaffStateEnum.NORMAL.getType());
        if (staff == null) {
            return JsonResult.error(Code.CHECK_ERROR, "不是管理员！");
        }
        // 获取Staff对象
        return JsonResult.success(orderAssignmentService.saveOrderAssignment(orderAssignmentParam.getOrderNo(),
                staff, orderAssignmentParam.getStaffNoList()));
    }
}
