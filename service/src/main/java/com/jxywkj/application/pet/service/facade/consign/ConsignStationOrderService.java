package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.Order;

import java.util.List;

/**
 * <p>
 * 站点订单
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className ConsignStationOrderService
 * @date 2019/12/18 8:51
 **/
public interface ConsignStationOrderService {
    /**
     * <p>
     * 通过站点编号以及类型查询订单列表
     * </p>
     *
     * @param stationNo 站点编号
     * @param state     订单状态
     * @param offset    排除条数
     * @param limit     显示条数
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Order>
     * @author LiuXiangLin
     * @date 8:52 2019/12/18
     **/
    List<Order> listByStationNoAndType(int stationNo, String customerNo, List state,
                                       String keyword, String orderDate, int offset, int limit);
}
