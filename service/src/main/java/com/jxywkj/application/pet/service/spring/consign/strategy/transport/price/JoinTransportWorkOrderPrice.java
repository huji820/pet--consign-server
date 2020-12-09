package com.jxywkj.application.pet.service.spring.consign.strategy.transport.price;


import com.jxywkj.application.pet.model.consign.Transport;
import com.jxywkj.application.pet.model.consign.params.OfflineWorkOrderDTO;
import com.jxywkj.application.pet.model.consign.params.OrderPrice;
import com.jxywkj.application.pet.service.facade.consign.BeyondPetNumService;

import javax.annotation.Resource;

/**
 * <p>
 * 工单合作价
 * </p>
 *
 * @author GuoPengCheng
 * @version 1.0
 * @className JoinTransportWorkOrderPrice
 * @date 2020/7/17 18:06
 **/
public class JoinTransportWorkOrderPrice extends  AbstractTransportWorkOrderPrice{
    /**
     * 工单DTO
     */
    private OfflineWorkOrderDTO offlineWorkOrderDTO;

    /**
     * 运输路线
     */
    private Transport transport;
    /**
     *
     * @param transport 运输路线
     * @param offlineWorkOrderDTO 工单DTO
     * @return
     */
    @Resource
    BeyondPetNumService beyondPetNumService;

    @Override
    public OrderPrice calWorkOrderPrice(Transport transport, OfflineWorkOrderDTO offlineWorkOrderDTO) {
        this.offlineWorkOrderDTO = offlineWorkOrderDTO;
        this.transport = transport;

        OrderPrice result = OrderPrice.newZeroInstance();


        return null;
    }
}
