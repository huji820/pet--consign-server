package com.jxywkj.application.pet.service.spring.consign.strategy.transport.price;

import com.jxywkj.application.pet.model.consign.AddedVolumeCage;
import com.jxywkj.application.pet.model.consign.BeyondPetNum;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.model.consign.Transport;
import com.jxywkj.application.pet.model.consign.params.OrderDTO;
import com.jxywkj.application.pet.model.consign.params.OrderPrice;
import com.jxywkj.application.pet.service.facade.consign.BeyondPetNumService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * <p>
 * 客户价格
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className RetailTransportOrderPrice
 * @date 2020/4/10 9:36
 **/
@Component
public class RetailTransportOrderPrice extends AbstractTransportOrderPrice {
    private Transport transport;
    private OrderDTO orderDTO;

    @Resource
    BeyondPetNumService beyondPetNumService;

    @Override
    public OrderPrice calcOrderPrice(Transport transport, OrderDTO orderDTO, Station station) throws Exception {
        this.transport = transport;
        this.orderDTO = orderDTO;

        OrderPrice result = OrderPrice.newZeroInstance();

        // 计算笼具价格
        AddedVolumeCage addedVolumeCage = getCage(station);
        TransportDTO transportDTO = new TransportDTO();
        transportDTO.setContinuePrice(transport.getContinueRetailPrice());
        transportDTO.setStartingWeight(transport.getStartingWeight());
        transportDTO.setBuyCage(1 == orderDTO.getBuyPetCage());
        transportDTO.setPetWeight(orderDTO.getWeight());
        transportDTO.setStartingPrice(transport.getStartingRetailPrice());

        if (addedVolumeCage != null) {
            transportDTO.setUseVolume(addedVolumeCage.getStartingRetailPrice() != null);
            transportDTO.setExistCage(Boolean.TRUE);
            transportDTO.setAddedWeightCage(addedVolumeCage.getAddedWeightCage());
            transportDTO.setVolumeCageAmount(addedVolumeCage.getStartingRetailPrice());
        }
        calcCage(result, transportDTO);

        // 计算保价金额
        calcInsure(result, orderDTO);

        // 计算上门接宠金额
        result.setReceiptAmount(calcReceiptPrice(station));
        result.setOrderAmount(result.getOrderAmount().add(result.getReceiptAmount().getAmount()));

        // 计算送宠上门金额
        result.setSendAmount(calcSendPrice());
        result.setOrderAmount(result.getOrderAmount().add(result.getSendAmount().getAmount()));

        // 计算超出数量的加价
        BeyondPetNum beyondPetNum = beyondPetNumService.getByTransportNo(station, transport.getTransportNo());
        // 超出数量价格
        if (orderDTO.getNum() > 1 && beyondPetNum != null) {
            result.setOrderAmount(result.getOrderAmount().add(BigDecimal.valueOf(orderDTO.getNum() - 1L).multiply(beyondPetNum.getRetailPrice())));
        }
        //包含猫猫套餐，价格增加20
        if(orderDTO.getGiveFood() != null && orderDTO.getGiveFood().equals(1)){
            result.setOrderAmount(result.getOrderAmount().add(new BigDecimal(20)));
        }
        return result;

    }

    @Override
    public OrderDTO getOrderDTO() {
        return orderDTO;
    }

    @Override
    public Transport getTransport() {
        return transport;
    }
}
