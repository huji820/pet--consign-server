package com.jxywkj.application.pet.service.spring.consign.strategy.transport.price;

import com.jxywkj.application.pet.model.consign.Transport;
import com.jxywkj.application.pet.model.consign.params.OfflineWorkOrderDTO;
import com.jxywkj.application.pet.model.consign.params.OrderPrice;

/**
 * 工单运输价格
 * @author GuoPengCheng
 * @version 1.0
 * @className AbstractTransportWorkOrderPrice
 * @date 2020/7/17 17:48
 */
public abstract class AbstractTransportWorkOrderPrice {

    /***
     * <p>
     * 计算订单价格
     * </p>
     *
     * @param transport 运输路线
     * @param offlineWorkOrderDTO 工单DTO
     * @return com.jxywkj.application.pet.model.consign.params.OrderPrice
     * @author GuoPengCheng
     * @date 17:50 2020/7/17
     **/
    public abstract OrderPrice calWorkOrderPrice(Transport transport, OfflineWorkOrderDTO offlineWorkOrderDTO);
}
