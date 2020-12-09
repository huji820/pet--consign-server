package com.jxywkj.application.pet.service.facade.consign;


import com.jxywkj.application.pet.model.consign.Order;

/**
 * <p>
 * 运输投保流水
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className ConsignInsureFlowService
 * @date 2019/12/30 18:42
 **/
public interface ConsignInsureFlowService {
    /**
     * <p>
     * 添加一条投保信息
     * </p>
     *
     * @param order 订单对象
     * @return int
     * @author LiuXiangLin
     * @date 18:54 2019/12/30
     **/
    int save(Order order);
}
