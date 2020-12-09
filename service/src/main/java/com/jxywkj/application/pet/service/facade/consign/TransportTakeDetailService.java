package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.TransportTakeDetail;

import java.util.List;

/**
 * <p>
 * 运输路线提货详情
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className TransportTakeDetailService
 * @date 2020/4/21 9:57
 **/
public interface TransportTakeDetailService {
    /**
     * <p>
     * 新增或者修改数据
     * </p>
     *
     * @param transportTakeDetail 运输录像提货详情
     * @return int
     * @author LiuXiangLin
     * @date 9:58 2020/4/21
     **/
    int saveOrUpdate(TransportTakeDetail transportTakeDetail) throws Exception;

    /**
     * <p>
     * 通过通过运输路线查询提货详情配置
     * </p>
     *
     * @param transportNo 运输路线
     * @param code        航空公司二字码
     * @return com.jxywkj.application.pet.model.consign.TransportTakeDetail
     * @author LiuXiangLin
     * @date 10:00 2020/4/21
     **/
    List<TransportTakeDetail> listByTransportNoAndCode(int transportNo, String code);

    /**
     * 通过订单编号查询提货详情配置
     * @param orderNo
     * @return
     */
    List<TransportTakeDetail> listByOrderNo(String orderNo);
}
