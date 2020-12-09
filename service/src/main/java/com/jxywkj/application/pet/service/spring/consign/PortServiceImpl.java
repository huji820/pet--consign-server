package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.common.utils.CollectionUtils;
import com.jxywkj.application.pet.dao.consign.PortMapper;
import com.jxywkj.application.pet.model.consign.*;
import com.jxywkj.application.pet.model.consign.params.StaffOrderQueryDTO;
import com.jxywkj.application.pet.service.facade.consign.*;
import com.yangwang.sysframework.utils.JsonUtil;
import com.yangwang.sysframework.utils.TypeConvertUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName PortServiceImpl
 * @Author LiuXiangLin
 * @Date 2019/8/9 14:14
 * @Version 1.0
 **/
@Service
public class PortServiceImpl implements PortService {

    @Resource
    PortMapper portMapper;

    @Resource
    ConsignOrderService consignOrderService;

    @Resource
    OrderAssignmentService assignmentService;

    @Resource
    StationService stationService;

    @Resource
    StaffService staffService;

    @Resource
    ConsignOrderLedgerService consignOrderLedgerService;

    @Resource
    TransportService transportService;

    @Override
    public List<Order> listPortOrderByType(StaffOrderQueryDTO staffOrderQueryDTO) {
        return portMapper.listByOrderTypeAndStation(staffOrderQueryDTO);
    }

    @Override
    public List<Order> listAdminOrderByType(StaffOrderQueryDTO staffOrderQueryDTO) {
        List<Order> orders = portMapper.listAdminByOrderTypeAndStation(staffOrderQueryDTO);

        if (!CollectionUtils.isEmpty(orders)) {
            for (Order order : orders) {
                // 查询已分配的员工
                order.setOrderAssignments(assignmentService.listByOrderNo(order.getOrderNo()));
            }
        }

        //获取员工身份，如果是司机只能获取自己被分配的单子
        Staff staff = staffService.getByStaffNo(Integer.valueOf(staffOrderQueryDTO.getStaffNo()));
        if(staff == null){
            return null;
        }
        if(staff.getRole() != 3){  //管理员或客服可以查看所有单子
            return orders;
        }else{   //司机身份
            List<Order> orderList = new ArrayList<>();
            for (Order order : orders) {
                List<OrderAssignment> orderAssignments = order.getOrderAssignments();
                if(orderAssignments != null){
                    for(OrderAssignment orderAssignment : orderAssignments){
                        Staff staff2 = orderAssignment.getStaff();
                        if(staff2 != null && staff.getStaffNo() == staff2.getStaffNo()){
                           orderList.add(order);
                        }
                    }
                }
            }
            return orderList;
        }
    }

    @Override
    public List<Order> listStaffOrderListByParam(String queryParamStr) throws Exception {
        List<Order> result = null;
        // 将json字符串转为对象
        StaffOrderQueryDTO staffOrderQueryDTO = JsonUtil.formObject(queryParamStr, StaffOrderQueryDTO.class);
        if (staffOrderQueryDTO != null) {
            if (staffOrderQueryDTO.getOrderTypeArray() != null && staffOrderQueryDTO.getOrderTypeArray().length > 0 && staffOrderQueryDTO.getStaffNo() != null) {
                // 管理员
                result = this.listAdminOrderByType(staffOrderQueryDTO);
                // 分离列表
                if (!CollectionUtils.isEmpty(result)) {
                    for (Order order : result) {
                        //没有运输路线
                        if(order.getTransport()==null||order.getTransport().getTransportNo()==-1){
                            Transport transport = transportService.getTransportByOrderNo(order.getOrderNo());
                            order.setTransport(transport);
                        }
                        ((ConsignOrderServiceImpl) consignOrderService).separateMedia(order);
                    }
                }
            }
        }
        return result;
    }
}
