package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.common.utils.CollectionUtils;
import com.jxywkj.application.pet.dao.consign.ConsignStationOrderMapper;
import com.jxywkj.application.pet.model.consign.Order;
import com.jxywkj.application.pet.model.consign.OrderAssignment;
import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.service.facade.consign.ConsignStationOrderService;
import com.jxywkj.application.pet.service.facade.consign.OrderAssignmentService;
import com.jxywkj.application.pet.service.facade.consign.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className ConsignStationOrderServiceImpl
 * @date 2019/12/18 8:53
 **/
@Service
public class ConsignStationOrderServiceImpl implements ConsignStationOrderService {
    @Resource
    ConsignStationOrderMapper consignStationOrderMapper;

    @Resource
    StaffService staffService;

    @Resource
    OrderAssignmentService assignmentService;

    @Override
    public List<Order> listByStationNoAndType(int stationNo, String customerNo, List state,
                                              String keyword, String orderDate, int offset, int limit) {
        if(keyword==null||keyword.equals("null")||keyword.equals(" ")){
            keyword = null;
        }
        if(orderDate==null||orderDate.equals(" ")||orderDate.equals("null")){
            orderDate = null;
        }
        List<Order> orderList = consignStationOrderMapper.listByStationNoAndType(stationNo, state, keyword, orderDate, offset, limit);
        if (!CollectionUtils.isEmpty(orderList)) {
            for (Order order : orderList) {
                // 查询已分配的员工
                order.setOrderAssignments(assignmentService.listByOrderNo(order.getOrderNo()));
            }
        }
        //获取员工
        Staff staff = staffService.getByCustomerNo(customerNo);
        if(staff.getRole() != 3){  //管理员或客服可以查看所有单子
            return orderList;
        }else{   //司机身份
            List<Order> orders = new ArrayList<>();
            for (Order order : orderList) {
                List<OrderAssignment> orderAssignments = order.getOrderAssignments();
                if(orderAssignments != null){
                    for(OrderAssignment orderAssignment : orderAssignments){
                        Staff staff2 = orderAssignment.getStaff();
                        if(staff2 != null){
                            orders.add(order);
                        }
                    }
                }
            }
            return orders;
        }
    }
}
