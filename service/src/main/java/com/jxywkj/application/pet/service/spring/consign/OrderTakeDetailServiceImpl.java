package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.common.enums.TransportTypeEnum;
import com.jxywkj.application.pet.dao.consign.OrderTakeDetailMapper;
import com.jxywkj.application.pet.model.consign.Order;
import com.jxywkj.application.pet.model.consign.OrderTakeDetail;
import com.jxywkj.application.pet.model.consign.params.LonAndLat;
import com.jxywkj.application.pet.service.facade.consign.ConsignOrderService;
import com.jxywkj.application.pet.service.facade.consign.OrderTakeDetailService;
import com.jxywkj.application.pet.service.facade.consign.TransportTakeDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className OrderTakeDetailServiceImpl
 * @date 2020/4/8 9:54
 **/
@Service
public class OrderTakeDetailServiceImpl implements OrderTakeDetailService {
    @Resource
    OrderTakeDetailMapper orderTakeDetailMapper;

    @Resource
    TransportTakeDetailService transportTakeDetailService;

    @Resource
    ConsignOrderService consignOrderService;

    @Override
    public int save(OrderTakeDetail orderTakeDetail) throws Exception {
        // 获取订单
        Order order = consignOrderService.getConsignOrderByOrderNo(orderTakeDetail.getOrder().getOrderNo());

        // 设置必须信息
        this.setFull(order, orderTakeDetail);

        // 更新提货位置配置(注意位置不能调换，下个方法需要用到这个方法里的逻辑)
        transportTakeDetailService.saveOrUpdate(orderTakeDetail);

        // 插入数据
        orderTakeDetailMapper.saveOrUpdate(orderTakeDetail);

        return 1;
    }

    @Override
    public OrderTakeDetail getByOrderNo(String orderNo) {
        return orderTakeDetailMapper.getByOrderNo(orderNo);
    }

    private void setFull(Order order, OrderTakeDetail orderTakeDetail) {
        if (order == null) {
            throw new RuntimeException("订单不存在！");
        }
        orderTakeDetail.setTransport(order.getTransport());
    }
}
