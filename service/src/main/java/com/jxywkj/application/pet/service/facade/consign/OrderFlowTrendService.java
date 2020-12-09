package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.OrderFlowTrend;

import java.util.List;

/**
 * <p>
 * 订单流水
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className OrderFlowTrendService
 * @date 2020/1/7 16:32
 **/
public interface OrderFlowTrendService {
    /**
     * <p>
     * 通过订单编号查询
     * </p>
     *
     * @param orderNo 订单编号
     * @return java.util.List<com.jxywkj.application.pet.model.consign.OrderFlowTrend>
     * @author LiuXiangLin
     * @date 16:33 2020/1/7
     **/
    List<OrderFlowTrend> listByOrderNo(String orderNo);
}
