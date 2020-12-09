package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.common.enums.OrderStateEnum;
import com.jxywkj.application.pet.common.enums.OrderStatusEnum;
import com.jxywkj.application.pet.common.utils.CollectionUtils;
import com.jxywkj.application.pet.dao.consign.ConsignOrderLedgerMapper;
import com.jxywkj.application.pet.model.consign.OrderLedger;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.model.consign.Transport;
import com.jxywkj.application.pet.service.facade.business.AreaService;
import com.jxywkj.application.pet.service.facade.consign.*;
import com.yangwang.sysframework.utils.DateUtil;
import com.yangwang.sysframework.utils.TypeConvertUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.TypeUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author Administrator
 * @Date 2019-12-20 22:20
 * @Version 1.0
 */
@Service
public class ConsignOrderLedgerServiceImpl implements ConsignOrderLedgerService {

    @Resource
    ConsignOrderLedgerMapper consignOrderLedgerMapper;

    @Resource
    TransportService transportService;

    @Resource
    ConsignOrderService consignOrderService;

    @Resource
    StationService stationService;

    @Resource
    OrderStateService orderStateService;

    @Resource
    AreaService areaService;

    @Override
    public int insertOrderLedger(OrderLedger... orderLedgers) {
        return consignOrderLedgerMapper.insertOrderLedger(orderLedgers);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertAndSyncOrderLedger(List<OrderLedger> orderLedgers, Station station) throws Exception {

        for (OrderLedger orderLedger : orderLedgers) {

            //通过起始城市获取站点，仅适用于单站点
            List<Station> stations = stationService.listStation(orderLedger.getStartCity());
            if(stations != null && stations.size() > 0){
                station = stations.get(0);
            }

            // 设置站点为起始城市的站点
            orderLedger.setStation(station);

            // 订单单号
            orderLedger.setOrderNo(consignOrderService.getOrderNo(new Date(), consignOrderService.getCityShortName(station.getStartCity())));

            // 订单生成时间
            orderLedger.setDateCreate(DateUtil.formatFull(new Date()));

            //获取开始城市id
            Integer cityId = areaService.getCityId(orderLedger.getStartCity());

            // 获取运输路线
            Transport transport = transportService.getTransport(cityId, station.getStartCity(), orderLedger.getEndCity(), orderLedger.getTransportType());
            if (transport == null) {  //如果路线为null，设定为（开始城市-结束城市）
                transport = Transport.defaultTransport(orderLedger.getStartCity(), orderLedger.getEndCity(), TypeConvertUtil.$Str(orderLedger.getTransportType()));
            }

            // 插入账簿
            this.insertOrderLedger(orderLedger);

            // 如果需要同步
            if (orderLedger.isSynced()) {
                consignOrderService.insertConsignLedgerOrder(orderLedger, transport);
            }
        }

        return 1;
    }

    @Override
    public OrderLedger getByOrderNo(String orderNo) {
        return consignOrderLedgerMapper.getByOrderNo(orderNo);
    }

    @Override
    public List<OrderLedger> listOrderLedger(int stationNo, String startDate, String endDate, String endCity, String petTypeName, String senderName, String senderPhone, String receiverName, String receiverPhone, boolean sync, int start, int limit) {
        return consignOrderLedgerMapper.listOrderLedger(stationNo, startDate, endDate, endCity, petTypeName, senderName, senderPhone, receiverName, receiverPhone, sync, start, limit);
    }

    @Override
    public int countOrderLedger(int stationNo, String startDate, String endDate, String endCity, String petTypeName, String senderName, String senderPhone, String receiverName, String receiverPhone, boolean sync) {
        return consignOrderLedgerMapper.countOrderLedger(stationNo, startDate, endDate, endCity, petTypeName, senderName, senderPhone, receiverName, receiverPhone, sync);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateOrderLedger(OrderLedger orderLedger) {
        return consignOrderLedgerMapper.updateOrderLedger(orderLedger);
    }

    @Override
    public int updateOrderLedgerSync(int stationNo, String orderNo) {
        return consignOrderLedgerMapper.updateOrderLedgerSync(stationNo, orderNo);
    }

    @Override
    public int deleteOrderLedger(String orderNo, int stationNo) {
        return consignOrderLedgerMapper.deleteOrderLedger(orderNo, stationNo);
    }
}
