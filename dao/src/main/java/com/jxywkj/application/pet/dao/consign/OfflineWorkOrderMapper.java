package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.OfflineOrder;
import com.jxywkj.application.pet.model.consign.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @ClassName OfflineWorkOrderMapper
 * @Description: 线下生成工单Mapper
 * @Author GuoPengCheng
 * @Date 2020/7/17 13:44
 * @Version 1.0
 **/
@Component
public interface OfflineWorkOrderMapper {

    /**
     * @return void
     * @Author GuoPengCheng
     * @Description 新增线下生成工单
     * @Date 13:46 2020/7/17
     * @Param [OfflineOrder]
     **/
    void insertOfflineWorkOrder(@Param("order")OfflineOrder order);


    /**
     * @Author GuoPengCheng
     * @param orderNo
     * @return 工单价格
     * @Description 新增通过订单号和工单标识符获取工单价格
     * @Date16：02 2020/07/22
     */
    Integer getOfflineWorkOrderPrice(@Param("orderNo")String orderNo);
}
