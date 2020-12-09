package com.jxywkj.application.pet.consign;

import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.service.facade.consign.ConsignOrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

import static com.jxywkj.application.pet.common.enums.Code.CHECK_ERROR;

/**
 * @ClassName ConsignOrderController
 * @Description: 托运后端订单Controller
 * @Author Aze
 * @Date 2019/7/18 17:41
 * @Version 1.0
 **/
@RestController
@RequestMapping("/consign/order")
public class ConsignOrderController {

    @Resource
    ConsignOrderService consignOrderService;

    @GetMapping("/listStationList")
    public JsonResult listOrder(@SessionAttribute("staff") Staff staff) {
        return JsonResult.success(consignOrderService.listOrdersByStationNo(staff.getStation().getStationNo()));
    }

    @PutMapping("/updatePrice")
    public JsonResult updatePrice(@RequestParam("orderNo") String orderNo,
                                  @RequestParam("beforeAmount") BigDecimal beforeAmount,
                                  @RequestParam("afterAmount") BigDecimal afterAmount) {

        int result = consignOrderService.updateOrderPrice(orderNo, beforeAmount, afterAmount);

        return result == 0 ? JsonResult.error(CHECK_ERROR) : JsonResult.success();
    }
}
