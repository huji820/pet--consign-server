package com.jxywkj.application.pet.service.spring.consign.strategy.transport.price;

import com.jxywkj.application.pet.common.utils.CollectionUtils;
import com.jxywkj.application.pet.common.utils.StringUtils;
import com.jxywkj.application.pet.model.consign.*;
import com.jxywkj.application.pet.model.consign.params.LonAndLat;
import com.jxywkj.application.pet.model.consign.params.OrderDTO;
import com.jxywkj.application.pet.model.consign.params.OrderPrice;
import com.jxywkj.application.pet.service.facade.consign.*;
import com.jxywkj.application.pet.service.spring.consign.GaoDeMapUtils;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.Bidi;
import java.util.List;

/**
 * <p>
 * 合作价
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className JoinTransportOrderPrice
 * @date 2020/4/10 9:36
 **/
@Component
public class JoinTransportOrderPrice extends AbstractTransportOrderPrice {
    /**
     * 订单DTO
     */
    private OrderDTO orderDTO;
    /**
     * 运输路线
     */
    private Transport transport;

    @Resource
    AddedVolumeCageService addedVolumeCageService;

    @Resource
    BeyondPetNumService beyondPetNumService;

    @Override
    public OrderPrice calcOrderPrice(Transport transport, OrderDTO orderDTO,Station station) throws Exception {
        this.orderDTO = orderDTO;
        this.transport = transport;

        OrderPrice result = OrderPrice.newZeroInstance();

        // 计算笼具价格
        AddedVolumeCage addedVolumeCage = getCage(station);
        TransportDTO transportDTO = new TransportDTO();
        transportDTO.setContinuePrice(transport.getContinueJoinPrice());
        transportDTO.setStartingWeight(transport.getStartingWeight());
        transportDTO.setBuyCage(1 == orderDTO.getBuyPetCage());
        transportDTO.setPetWeight(orderDTO.getWeight());
        transportDTO.setStartingPrice(transport.getStartingJoinPrice());

        if (addedVolumeCage != null) {
            transportDTO.setExistCage(Boolean.TRUE);
            transportDTO.setUseVolume(addedVolumeCage.getStartingJoinPrice() != null);
            transportDTO.setAddedWeightCage(addedVolumeCage.getAddedWeightCage());
            transportDTO.setVolumeCageAmount(addedVolumeCage.getStartingJoinPrice());
        }
        calcCage(result, transportDTO);

        // 计算保价金额
        calcInsure(result, orderDTO);


        // 计算上门接宠价格
        result.setReceiptAmount(calcReceiptPrice(station));
        result.setOrderAmount(result.getOrderAmount().add(result.getReceiptAmount().getAmount()));

        // 计算送宠上门价格
        result.setSendAmount(calcSendPrice());
        result.setOrderAmount(result.getOrderAmount().add(result.getSendAmount().getAmount()));

        // 计算超出数量的加价
        BeyondPetNum beyondPetNum = beyondPetNumService.getByTransportNo(station, transport.getTransportNo());
        // 超出数量价格
        if (orderDTO.getNum() > 1 && beyondPetNum != null) {
            result.setOrderAmount(result.getOrderAmount().add(BigDecimal.valueOf(orderDTO.getNum() - 1L).multiply(beyondPetNum.getJoinPrice())));
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
