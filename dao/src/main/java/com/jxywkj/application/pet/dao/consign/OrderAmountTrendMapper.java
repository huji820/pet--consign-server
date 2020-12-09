package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.OrderAmountTrend;
import com.jxywkj.application.pet.model.consign.params.OrderAmountTrendDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单去向
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className OrderAmountTrendMapper
 * @date 2020/1/6 15:19
 **/
@Mapper
public interface OrderAmountTrendMapper {

    /**
     * <p>
     * 查询站点订单
     * </p>
     *
     * @param orderAmountTrendDTO 查询参数
     * @return java.util.List<com.jxywkj.application.pet.model.consign.OrderAmountTrend>
     * @author LiuXiangLin
     * @date 15:38 2020/1/6
     **/
    List<OrderAmountTrend> listByParam(@Param("orderAmountTrendDTO") OrderAmountTrendDTO orderAmountTrendDTO);

    /**
     * <p>
     * 查询总条数
     * </p>
     *
     * @param orderAmountTrendDTO 查询参数对象
     * @return int
     * @author LiuXiangLin
     * @date 17:03 2020/1/6
     **/
    int getTotalByParam(@Param("orderAmountTrendDTO") OrderAmountTrendDTO orderAmountTrendDTO);
}
