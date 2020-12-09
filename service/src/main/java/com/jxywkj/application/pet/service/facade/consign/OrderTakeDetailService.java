package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.OrderTakeDetail;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className OrderTakeDetailService
 * @date 2020/4/8 9:51
 **/
public interface OrderTakeDetailService {
    /**
     * <p>
     * 新增一条信息
     * </p>
     *
     * @param orderTakeDetail 收货信息
     * @return int
     * @author LiuXiangLin
     * @date 9:52 2020/4/8
     **/
    int save(OrderTakeDetail orderTakeDetail) throws Exception;

    /**
     * <p>
     * 通过订单编号查询数据
     * </p>
     *
     * @param orderNo 订单编号
     * @return com.jxywkj.application.pet.model.consign.OrderTakeDetail
     * @author LiuXiangLin
     * @date 9:52 2020/4/8
     **/
    OrderTakeDetail getByOrderNo(String orderNo);
}
