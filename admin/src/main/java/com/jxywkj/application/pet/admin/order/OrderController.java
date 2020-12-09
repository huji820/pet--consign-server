package com.jxywkj.application.pet.admin.order;

import com.jxywkj.application.pet.model.consign.Order;
import com.jxywkj.application.pet.model.consign.vo.OrderVO;
import com.jxywkj.application.pet.model.dto.OrderQueryDto;
import com.jxywkj.application.pet.service.facade.consign.ConsignOrderService;
import com.yangwang.sysframework.utils.Pagination;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description
 * @Author zxj
 * @Date 2020/10/14 10:14
 * @Version 1.0
 */
@RestController("adminOrderController")
@RequestMapping("/admin/order/info")
public class OrderController {

    @Resource
    ConsignOrderService orderService;

    @GetMapping("")
    public Pagination listOrder(@RequestParam(value = "orderNo", required = false) String orderNo,
                                @RequestParam(value = "stationNo", required = false) Integer stationNo,
                                @RequestParam(value = "cityName", required = false) String cityName,
                                @RequestParam(value = "orderStartDate", required = false) String orderStartDate,
                                @RequestParam(value = "orderEndDate", required = false) String orderEndDate,
                                @RequestParam(value = "leaveStartDate", required = false) String leaveStartDate,
                                @RequestParam(value = "leaveEndDate", required = false) String leaveEndDate,
                                 int start, int limit){
        Pagination pagination = new Pagination();
        OrderQueryDto dto = new OrderQueryDto();
        dto.setOrderNo(orderNo);
        dto.setStationNo(stationNo);
        dto.setCityName(cityName);
        dto.setLeaveStartDate(leaveStartDate);
        dto.setLeaveEndDate(leaveEndDate);
        dto.setOrderStartDate(orderStartDate);
        dto.setOrderEndDate(orderEndDate);

        dto.setStart(start);
        dto.setLimit(limit);
        List<OrderVO> orderList = orderService.listAdminOrder(dto);
        int i = orderService.countListAdminOrder(dto);
        pagination.setRoot(orderList);
        pagination.setTotalCount(i);
        return pagination;
    }
}
