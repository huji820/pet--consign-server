package com.jxywkj.application.pet.consign;

import com.alibaba.fastjson.JSONObject;
import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.service.facade.consign.OrderAssignmentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName OrderAssignmentController
 * @Description 订单分配
 * @Author LiuXiangLin
 * @Date 2019/8/24 14:32
 * @Version 1.0
 **/
@RestController
@RequestMapping("/consign/orderAssignment")
public class OrderAssignmentController {

    @Resource
    OrderAssignmentService orderAssignmentService;

    @PostMapping("/save")
    public JsonResult save(@RequestBody JSONObject jsonObject,
                           @SessionAttribute(value = "staff") Staff staff) {

        return JsonResult.success(orderAssignmentService.saveOrderAssignment((String) jsonObject.get("orderNo"), staff, (List) (jsonObject.get("staffNo"))));
    }

    @GetMapping("/listAll")
    public JsonResult listAll(@SessionAttribute("staff") Staff staff) {
        return JsonResult.success(orderAssignmentService.listByCityName(staff.getStation().getCity()));
    }
}
