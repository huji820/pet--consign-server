package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.OrderLedger;
import com.jxywkj.application.pet.model.consign.Station;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ConsignOrderLedgerService {

    /**
     * 查询订单帐页
     * @param stationNo
     * @param startDate
     * @param endDate
     * @param endCity
     * @param petTypeName
     * @param senderName
     * @param senderPhone
     * @param receiverName
     * @param receiverPhone
     * @param sync
     * @param start
     * @param limit
     * @return
     */
    List<OrderLedger> listOrderLedger(int stationNo, String startDate, String endDate, String endCity, String petTypeName, String senderName, String senderPhone, String receiverName, String receiverPhone, boolean sync, int start, int limit);

    /**
     * 订单帐页条目数
     * @param stationNo
     * @param startDate
     * @param endDate
     * @param endCity
     * @param petTypeName
     * @param senderName
     * @param senderPhone
     * @param receiverName
     * @param receiverPhone
     * @param sync
     * @return
     */
    int countOrderLedger(int stationNo, String startDate, String endDate, String endCity, String petTypeName, String senderName, String senderPhone, String receiverName, String receiverPhone, boolean sync);


    /**
     * 修改订单账单
     * @param orderLedger
     * @return
     */
    int updateOrderLedger(OrderLedger orderLedger);


    /**
     * 修改订单同步成功
     * @param stationNo
     * @param orderNo
     * @return
     */
    int updateOrderLedgerSync(int stationNo, String orderNo);

    /**
     * 删除订单账页
     * @param orderNo
     * @param stationNo
     * @return
     */
    int deleteOrderLedger(String orderNo, int stationNo);

    /**
     * 插入订单帐铺表
     * @param orderLedgers
     * @return
     */
    int insertOrderLedger(OrderLedger... orderLedgers);

    /**
     * 插入订单帐铺表
     * @param orderLedgers
     * @return
     */
    int insertAndSyncOrderLedger(List<OrderLedger> orderLedgers, Station station) throws Exception;

    /**
     * 通过订单编号查询订单账单
     * @param orderNo
     * @return
     */
    OrderLedger getByOrderNo(String orderNo);
}
