package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 站点订单
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className ConsignStationOrderMapper
 * @date 2019/12/18 8:54
 **/
@Mapper
public interface ConsignStationOrderMapper {
    /**
     * <p>
     * 通过站点编号以及订单状态查询订单列表
     * </p>
     *
     * @param stationNo 站点编号
     * @param state     订单状态
     * @param offset    排除条数
     * @param limit     显示条数
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Order>
     * @author LiuXiangLin
     * @date 8:56 2019/12/18
     **/
    List<Order> listByStationNoAndType(@Param("stationNo") int stationNo,
                                       @Param("states") List state,
                                       @Param("keyword") String keyword,
                                       @Param("orderDate")String orderDate,
                                       @Param("offset") int offset,
                                       @Param("limit") int limit);

}
