package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.OrderLedger;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ConsignOrderLedgerMapper {

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
    List<OrderLedger> listOrderLedger(@Param("stationNo") int stationNo, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("endCity") String endCity, @Param("petTypeName") String petTypeName, @Param("senderName") String senderName, @Param("senderPhone") String senderPhone, @Param("receiverName") String receiverName, @Param("receiverPhone") String receiverPhone, @Param("sync") boolean sync, @Param("start") int start, @Param("limit") int limit);

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
    int countOrderLedger(@Param("stationNo") int stationNo, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("endCity") String endCity, @Param("petTypeName") String petTypeName, @Param("senderName") String senderName, @Param("senderPhone") String senderPhone, @Param("receiverName") String receiverName, @Param("receiverPhone") String receiverPhone, @Param("sync") boolean sync);

    /**
     * 批量插入订单
     * @param orderLedgers
     * @return
     */
    int insertOrderLedger(@Param("orderLedgers") OrderLedger... orderLedgers);

    /**
     * 删除订单账页
     * @param orderNo
     * @param stationNo
     * @return
     */
    int deleteOrderLedger(@Param("orderNo") String orderNo, @Param("stationNo") int stationNo);

    /**
     * 修改订单账单
     * @param orderLedger
     * @return
     */
    int updateOrderLedger(@Param("orderLedger") OrderLedger orderLedger);

    /**
     * 修改订单同步成功
     * @param stationNo
     * @param orderNo
     * @return
     */
    int updateOrderLedgerSync(@Param("stationNo") int stationNo, @Param("orderNo") String orderNo);

    /**
     * 通过订单编号查询订单账单
     * @param orderNo
     * @return
     */
    OrderLedger getByOrderNo(@Param("orderNo")String orderNo);
}
