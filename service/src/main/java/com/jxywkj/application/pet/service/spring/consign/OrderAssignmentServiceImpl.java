package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.dao.consign.OrderAssignmentMapper;
import com.jxywkj.application.pet.model.consign.Order;
import com.jxywkj.application.pet.model.consign.OrderAssignment;
import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.model.consign.vo.OrderAssignmentVO;
import com.jxywkj.application.pet.service.facade.common.CustomerMessageService;
import com.jxywkj.application.pet.service.facade.consign.OrderAssignmentService;
import com.yangwang.sysframework.utils.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName OrderAssignmentServiceImpl
 * @Author LiuXiangLin
 * @Date 2019/8/24 14:26
 * @Version 1.0
 **/
@Service
public class OrderAssignmentServiceImpl implements OrderAssignmentService {
    @Resource
    OrderAssignmentMapper orderAssignmentMapper;

    @Resource
    CustomerMessageService customerMessageService;


    @Override
    public int saveOrderAssignment(String orderNo, Staff staff, List<Integer> staffNoArray) {
        orderAssignmentMapper.saveOrderAssignment(getOrderAssignment(orderNo, staff, staffNoArray));
        // 发送站点信息
        customerMessageService.sendOrderAssignmentMessage(staffNoArray);

        return staffNoArray.size();
    }

    @Override
    public List<OrderAssignmentVO> listByCityName(String cityName) {
        return orderAssignmentMapper.listByCityName(cityName);
    }

    @Override
    public List<OrderAssignment> listByOrderNo(String orderNo) {
        return orderAssignmentMapper.listByOrderNo(orderNo);
    }

    @Override
    public List<String> listPhoneByOrderNo(String orderNo) {
        return orderAssignmentMapper.listPhoneByOrderNo(orderNo);
    }

    @Override
    public int countByOrderNo(String orderNo) {
        return orderAssignmentMapper.countByOrderNo(orderNo);
    }

    /**
     * @return com.jxywkj.application.pet.model.consign.OrderAssignment
     * @Author LiuXiangLin
     * @Description 获取OrderAssignment对象
     * @Date 14:31 2019/8/24
     * @Param [orderNo, operator, staffNo]
     **/
    private OrderAssignment getOrderAssignment(String orderNo, Staff operator, List<Integer> staffNoArray) {
        if (staffNoArray == null || staffNoArray.size() == 0) {
            return null;
        }

        List<Staff> staffList = new ArrayList<>();

        OrderAssignment result = new OrderAssignment();

        Order order = new Order();
        order.setOrderNo(orderNo);

        Staff staff;

        for (int staffNo : staffNoArray) {
            staff = new Staff();
            staff.setStaffNo(staffNo);
            staffList.add(staff);
        }

        result.setOrder(order);
        result.setOperator(operator);
        result.setAssignmentTime(DateUtil.format(new Date(), DateUtil.FORMAT_FULL));
        result.setStaffList(staffList);

        return result;
    }

}
