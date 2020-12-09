package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.TransportTakeDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 运输路线订单详情
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className TransportTakeDetailMapper
 * @date 2020/4/21 9:19
 **/
public interface TransportTakeDetailMapper {
    /**
     * <p>
     * 新增一条数据
     * </p>
     *
     * @param transportTakeDetail 运输路线提货详情
     * @return int
     * @author LiuXiangLin
     * @date 9:22 2020/4/21
     **/
    int saveOrUpdate(@Param("transportTakeDetail") TransportTakeDetail transportTakeDetail);

    /**
     * <p>
     * 通过运输路线编号获取数据
     * </p>
     *
     * @param transportNo 运输路线编号
     * @param code 航空公司二字码
     * @return com.jxywkj.application.pet.model.consign.TransportTakeDetail
     * @author LiuXiangLin
     * @date 9:23 2020/4/21
     **/
    List<TransportTakeDetail> listByTransportNoAndCode(@Param("transportNo") int transportNo, @Param("code")String code);

    /**
     * 通过订单编号获取提货信息
     * @param orderNo
     * @return
     */
    List<TransportTakeDetail> listByOrderNo(@Param("orderNo")String orderNo);
}
