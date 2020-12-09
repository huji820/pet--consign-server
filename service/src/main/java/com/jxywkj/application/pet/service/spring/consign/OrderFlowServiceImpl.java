package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.dao.consign.OrderFlowMapper;
import com.jxywkj.application.pet.model.consign.OrderFlow;
import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.service.facade.consign.ConsignOrderService;
import com.jxywkj.application.pet.service.facade.consign.OrderFlowService;
import com.jxywkj.application.pet.service.facade.consign.StaffService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className OrderFlowServiceImpl
 * @date 2020/5/19 16:22
 **/
@Service
public class OrderFlowServiceImpl implements OrderFlowService {
    @Resource
    OrderFlowMapper orderFlowMapper;

    @Resource
    ConsignOrderService orderService;

    @Resource
    StaffService staffService;

    @Override
    public List<OrderFlow> listByStationNo(int stationNo, int offset, int limit) {
        List<OrderFlow> orderFlows = orderFlowMapper.listByStationNo(stationNo, offset, limit);
        if(orderFlows != null && orderFlows.size()>0){
            for(OrderFlow orderFlow : orderFlows){
                //获取订单所属员工
                if(orderFlow.getOrder() != null
                        && orderFlow.getOrder().getStaff() != null
                        && orderFlow.getOrder().getStaff().getStaffNo() != null){
                    Staff staff = staffService.getByStaffNo(orderFlow.getOrder().getStaff().getStaffNo());
                    orderFlow.getOrder().setStaff(staff);
                }
                //获取付款订单类型
                boolean offlinePayment = orderService.getOfflinePayment(orderFlow.getOrder().getOrderNo());
                if(offlinePayment){ //线下付款订单
                    orderFlow.getOrder().setOfflinePaymentLogo("1");
                }else{      //非线下付款订单
                    orderFlow.getOrder().setOfflinePaymentLogo("0");
                }
            }
        }
        return orderFlows;
    }

    @Override
    public List<OrderFlow> listByBusinessNo(String businessNo, int offset, int limit) {
        return orderFlowMapper.listByBusinessNo(businessNo, offset, limit);
    }

    @Override
    public OrderFlow getByOrderNoAndStationNo(String orderNo, String stationNo) {
        return orderFlowMapper.getByOrderNoAndStationNo(orderNo, stationNo);
    }
}
