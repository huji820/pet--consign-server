package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.dao.consign.OrderTransportMapper;
import com.jxywkj.application.pet.model.consign.OrderTransport;
import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.service.facade.consign.OrderTransportService;
import com.jxywkj.application.pet.service.facade.consign.StaffService;
import com.jxywkj.application.pet.service.facade.consign.StationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName OrderTransportServiceImpl
 * @Description
 * @Author LiuXiangLin
 * @Date 2019/9/23 14:36
 * @Version 1.0
 **/
@Service
public class OrderTransportServiceImpl implements OrderTransportService {

    @Resource
    OrderTransportMapper orderTransportMapper;

    @Resource
    StationService stationService;

    @Resource
    StaffService staffService;

    @Override
    public int saveOrderTransport(OrderTransport orderTransport) {
        // 获取用户信息
        Staff staff = staffService.getByStaffNo(orderTransport.getStaff().getStaffNo());
        orderTransport.setStaff(staff);

        // 获取站点信息
        Station station = stationService.getByPhone(orderTransport.getStaff().getPhone());
        orderTransport.setStation(station);

        return orderTransportMapper.saveOrderTransport(orderTransport);
    }

    @Override
    public List<OrderTransport> listByTransportNumAndDateTime(String transportNum, String startTime) {
        return orderTransportMapper.listByTransportNumAndDateTime(transportNum, startTime);
    }

    @Override
    public OrderTransport getLastByOrderNo(String orderNo) {
        return orderTransportMapper.getLastByOrderNo(orderNo);
    }

}
