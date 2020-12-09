package com.jxywkj.application.pet.service.facade.consign;

import com.alibaba.druid.support.spring.stat.annotation.Stat;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.model.consign.Transport;
import com.jxywkj.application.pet.model.consign.params.OfflineWorkOrderDTO;
import com.jxywkj.application.pet.model.consign.params.OrderDTO;
import com.jxywkj.application.pet.model.consign.params.OrderPrice;

import java.util.Date;

/**
 * @ClassName OfflineWorkOrderService
 * @Description: 线下生成订单Service
 * @Author GuoPengCheng
 * @Date 2020/7/17 11:23
 * @Version 1.0
 **/
public interface OfflineWorkOrderService {

    /**
     *新增线下工单
     * @param offlineWorkOrderDTO 线下生成工单
     * @return java.lang.String
     * @throws Exception
     * @author GuoPengCheng
     * @date 11:47 2020/07/17
     */
    String insertOfflineWorkOrder(OfflineWorkOrderDTO offlineWorkOrderDTO)throws Exception;
    /**
     * 生成单据编号
     *
     * @param date
     * @param cityNameAbbreviation
     * @return
     */
    String getOrderNo(Date date, String cityNameAbbreviation);
    /**
     * 获取城市的断码
     *
     * @param cityName
     * @return
     */
    String getCityShortName(String cityName);

    /**
     * @return java.math.BigDecimal
     * @Author GUOPengCheng
     * @Description 计算订单价格
     * @Date 15:35 2019/7/13
     * @Param []
     **/
    OrderPrice getOrderPrice(Transport transport, OfflineWorkOrderDTO offlineWorkOrderDTO, Station startStation) throws Exception;

    /***
     * <p>
     * 计算订单价格
     * </p>
     *
     * @param transport 运输路线
     * @param offlineWorkOrderDTO 订单DTO
     * @return com.jxywkj.application.pet.model.consign.params.OrderPrice
     * @author LiuXiangLin
     * @date 9:35 2020/4/10
     **/
//    public abstract OrderPrice calcOrderPrice(Transport transport, OfflineWorkOrderDTO offlineWorkOrderDTO) throws Exception;

    /**
     *  <p>
     * 计算工单价格
     * </p>
     * @param orderNo
     * @return java.long.Integer
     * @author GuoPengCheng
     * @date 15:57 2020/07/22
     */
    Integer getOfflineWorkOrderPrice(String orderNo);

}
