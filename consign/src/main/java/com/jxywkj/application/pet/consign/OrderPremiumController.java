package com.jxywkj.application.pet.consign;


import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.consign.OrderPremium;
import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.service.facade.consign.OrderPremiumService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author LiuXiangLin
 * @Description 补差价
 * @Date 9:05 2019/8/27
 * @Param
 * @return
 **/
@RestController
@RequestMapping("/consign/OrderSpread")
public class OrderPremiumController {

    @Autowired
    OrderPremiumService orderPremiumService;

    @ApiOperation("生成一个补价单的记录")
    @PostMapping("")
    public JsonResult insetOrderSpread(@RequestBody OrderPremium orderPremium, @SessionAttribute("staff") Staff staff) {
        orderPremiumService.insetOrderSpread(orderPremium, staff);
        return JsonResult.success("添加成功");
    }
}
