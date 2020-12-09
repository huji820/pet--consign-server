package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.common.utils.CollectionUtils;
import com.jxywkj.application.pet.dao.consign.OrderTempDeliverMapper;
import com.jxywkj.application.pet.model.consign.Order;
import com.jxywkj.application.pet.model.consign.OrderTempDeliver;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.model.consign.Transport;
import com.jxywkj.application.pet.model.consign.params.LonAndLat;
import com.jxywkj.application.pet.service.facade.consign.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName OrderTempDeliverServiceImpl
 * @Description
 * @Author LiuXiangLin
 * @Date 2019/9/25 10:03
 * @Version 1.0
 **/
@Service
public class OrderTempDeliverServiceImpl implements OrderTempDeliverService {
    @Resource
    OrderTempDeliverMapper orderTempDeliverMapper;

    @Resource
    ConsignOrderService consignOrderService;

    @Resource
    StationService stationService;

    @Resource
    TransportService transportService;

    @Resource
    GaoDeMapUtils gaoDeMapUtils;

    @Resource
    OrderStateService orderStateService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(OrderTempDeliver orderTempDeliver) throws Exception {
        // 获取订单
        Order order = consignOrderService.getConsignOrderByOrderNo(orderTempDeliver.getOrder().getOrderNo());
        // 获取运输路线
        Transport transport = transportService.getTransportByTransportNo(order.getTransport().getTransportNo());
        // 获取终点站点
        List<Station> stationList = stationService.listStation(transport.getEndCity());
        if (!CollectionUtils.isEmpty(stationList)) {
            Station station = stationList.get(0);
            // 获取完整地址
            String address = station.getProvince() + station.getCity() + orderTempDeliver.getAddress();
            // 获取经纬度
            LonAndLat lonAndLat = gaoDeMapUtils.getLonAndLat(address);

            // 添加一条正在派送中状态
            orderStateService.addDelivering(orderTempDeliver.getOrder().getOrderNo());

            // 设置必须参数
            orderTempDeliver.setStation(station);
            orderTempDeliver.setAddress(address);
            orderTempDeliver.setLongitude(Double.valueOf(lonAndLat.getLongitude()));
            orderTempDeliver.setLatitude(Double.valueOf(lonAndLat.getLatitude()));

            return orderTempDeliverMapper.save(orderTempDeliver);
        }
        return 0;
    }
}
