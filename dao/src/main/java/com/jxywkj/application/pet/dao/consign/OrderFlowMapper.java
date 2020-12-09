package com.jxywkj.application.pet.dao.consign;

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
 * @className OrderFlowMapper
 * @date 2020/5/19 14:42
 **/
public interface OrderFlowMapper {
    /**
     * <p>
     * 分页查找站点的订单流水
     * </p>
     *
     * @param stationNo 站点编号
     * @param offset    排除条数
     * @param limit     显示条数
     * @return java.util.List<com.jxywkj.application.pet.model.consign.OrderFlow>
     * @author LiuXiangLin
     * @date 14:44 2020/5/19
     **/
    List<OrderFlow> listByStationNo(@Param("stationNo") int stationNo, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * <p>
     * 分页查找商家的订单流水
     * </p>
     *
     * @param businessNo 商家编号
     * @param offset     排除条数
     * @param limit      显示条数
     * @return java.util.List<com.jxywkj.application.pet.model.consign.OrderFlow>
     * @author LiuXiangLin
     * @date 14:44 2020/5/19
     **/
    List<OrderFlow> listByBusinessNo(@Param("businessNo") String businessNo, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * @Description: 通过订单编号和站点编号获取站点流水
     * @Author: zxj
     * @Date: 2020/9/19 9:11
     * @param orderNo: 订单编号
     * @param stationNo: 站点编号
     * @return: com.jxywkj.application.pet.model.consign.OrderFlow
     **/
    OrderFlow getByOrderNoAndStationNo(@Param("orderNo")String orderNo,@Param("stationNo")String stationNo);
}
