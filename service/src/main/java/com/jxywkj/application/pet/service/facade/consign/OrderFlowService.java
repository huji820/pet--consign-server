package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.OrderFlow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单流水
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className OrderFlowService
 * @date 2020/5/19 16:20
 **/
public interface OrderFlowService {
    /**
     * <p>
     * 查询站点的订单流水
     * </p>
     *
     * @param stationNo 站点编号
     * @param offset    排除条数
     * @param limit     显示条数
     * @return java.util.List<com.jxywkj.application.pet.model.consign.OrderFlow>
     * @author LiuXiangLin
     * @date 16:21 2020/5/19
     **/
    List<OrderFlow> listByStationNo(int stationNo, int offset, int limit);

    /**
     * <p>
     * 查询商家订单流水
     * </p>
     *
     * @param businessNo 商家编号
     * @param offset     排除条数
     * @param limit      显示条数
     * @return java.util.List<com.jxywkj.application.pet.model.consign.OrderFlow>
     * @author LiuXiangLin
     * @date 17:07 2020/5/19
     **/
    List<OrderFlow> listByBusinessNo(String businessNo, int offset, int limit);

    /**
     * @Description: 通过订单编号和站点编号获取站点流水
     * @Author: zxj
     * @Date: 2020/9/19 9:11
     * @param orderNo: 订单编号
     * @param stationNo: 站点编号
     * @return: com.jxywkj.application.pet.model.consign.OrderFlow
     **/
    OrderFlow getByOrderNoAndStationNo(String orderNo, String stationNo);
}
