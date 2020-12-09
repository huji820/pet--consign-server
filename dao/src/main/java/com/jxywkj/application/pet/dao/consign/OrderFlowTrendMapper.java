package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.OrderFlowTrend;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className OrderFlowTrendMapper
 * @date 2020/1/7 16:21
 **/
@Mapper
public interface OrderFlowTrendMapper {
    /**
     * <p>
     * 通过站点编号获取订单流水
     * </p>
     *
     * @param orderNo 订单编号
     * @return java.util.List<com.jxywkj.application.pet.model.consign.OrderFlowTrend>
     * @author LiuXiangLin
     * @date 16:22 2020/1/7
     **/
    List<OrderFlowTrend> listByOrderNo(@Param("orderNo") String orderNo);
}
