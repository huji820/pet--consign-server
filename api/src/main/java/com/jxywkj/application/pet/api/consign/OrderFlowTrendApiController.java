package com.jxywkj.application.pet.api.consign;

import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.service.facade.consign.OrderFlowTrendService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className OrderFlowTrendApiController
 * @date 2020/1/7 16:35
 **/
@RestController
@RequestMapping("/api/order/flow/trend")
public class OrderFlowTrendApiController {
    @Resource
    OrderFlowTrendService orderFlowTrendService;

    @GetMapping("/order-no")
    public JsonResult listByOrderNo(@RequestParam("orderNo") String orderNo) {
        return JsonResult.success(orderFlowTrendService.listByOrderNo(orderNo));
    }
}
