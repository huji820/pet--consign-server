package com.jxywkj.application.pet.admin;

import com.yangwang.sysframework.utils.JsonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Administrator
 * @Date 2019-12-06 15:59
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin")
public class PermissionController {

    @RequestMapping("/menu")
    public JsonResult getMenu() {
        List<Map<String, Object>> result = new ArrayList<>();

        Map<String, Object> station = new HashMap<>();
        station.put("menuid", "1");
        station.put("text", "站点");
        station.put("sn", 2);
        station.put("open", true);
        result.add(station);

        Map<String, Object> stationList = new HashMap<>();
        stationList.put("menuid", "11");
        stationList.put("text", "站点管理");
        stationList.put("sn", 1);
        stationList.put("title", "基础资料");
        stationList.put("page", "core.coreApp.station.Station");
        stationList.put("open", true);
        result.add(stationList);

        Map<String, Object> staffList = new HashMap<>();
        staffList.put("menuid", "12");
        staffList.put("text", "员工管理");
        staffList.put("sn", 1);
        staffList.put("title", "基础资料");
        staffList.put("page", "core.coreApp.station.Staff");
        staffList.put("open", true);
        result.add(staffList);

        Map<String, Object> business = new HashMap<>();
        business.put("menuid", "2");
        business.put("text", "商家");
        business.put("sn", 2);
        business.put("open", true);
        result.add(business);

        Map<String, Object> businessList = new HashMap<>();
        businessList.put("menuid", "21");
        businessList.put("text", "商家管理");
        businessList.put("sn", 1);
        businessList.put("title", "基础资料");
        businessList.put("page", "core.coreApp.business.Business");
        businessList.put("open", true);
        result.add(businessList);

        Map<String, Object> customer = new HashMap<>();
        customer.put("menuid", "3");
        customer.put("text", "用户");
        customer.put("sn", 2);
        customer.put("open", true);
        result.add(customer);

        Map<String, Object> customerList = new HashMap<>();
        customerList.put("menuid", "31");
        customerList.put("text", "用户管理");
        customerList.put("sn", 1);
        customerList.put("title", "基础资料");
        customerList.put("page", "core.coreApp.customer.Customer");
        customerList.put("open", true);
        result.add(customerList);

        Map<String, Object> order = new HashMap<>();
        order.put("menuid", "4");
        order.put("text", "订单");
        order.put("sn", 2);
        order.put("open", true);
        result.add(order);

        Map<String, Object> orderList = new HashMap<>();
        orderList.put("menuid", "41");
        orderList.put("text", "订单管理");
        orderList.put("sn", 1);
        orderList.put("title", "基础资料");
        orderList.put("page", "core.coreApp.order.Order");
        orderList.put("open", true);
        result.add(orderList);

        Map<String, Object> fm = new HashMap<>();
        fm.put("menuid", "a");
        fm.put("text", "财务");
        fm.put("sn", 2);
        fm.put("open", true);
        result.add(fm);

        Map<String, Object> tixian1 = new HashMap<>();
        tixian1.put("menuid", "a1");
        tixian1.put("text", "站点提现管理");
        tixian1.put("page", "core.coreApp.fm.withdraw.Station");
        tixian1.put("leaf", true);
        tixian1.put("sn", 1);
        tixian1.put("title", "基础资料");
        tixian1.put("open", true);
        result.add(tixian1);

        Map<String, Object> tixian2 = new HashMap<>();
        tixian2.put("menuid", "a2");
        tixian2.put("text", "商家提现管理");
        tixian2.put("page", "core.coreApp.fm.withdraw.Business");
        tixian2.put("leaf", true);
        tixian2.put("sn", 1);
        tixian2.put("title", "基础资料");
        tixian2.put("open", true);
        result.add(tixian2);

        return JsonResult.success(result);
    }

}
