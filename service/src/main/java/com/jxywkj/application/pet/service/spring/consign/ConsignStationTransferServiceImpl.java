package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.dao.consign.ConsignStationTransferMapper;
import com.jxywkj.application.pet.model.consign.StationTransfer;
import com.jxywkj.application.pet.service.facade.consign.ConsignStationTransferService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 中转站点
 */
@Service
public class ConsignStationTransferServiceImpl implements ConsignStationTransferService {

    @Resource
    ConsignStationTransferMapper consignStationTransferMapper;

    @Override
    public int insertOrUpdateStationTransfer(String orderNo, String stationNo) {
        //通过订单号查看是否有中转站点
        StationTransfer transfer = getLastTransferByOrderNo(orderNo);

        StationTransfer stationTransfer = new StationTransfer();
        stationTransfer.setStationNo(stationNo);
        stationTransfer.setOrderNo(orderNo);

        if(transfer != null){
            stationTransfer.setSn(transfer.getSn()+1);
            return consignStationTransferMapper.insertOrUpdateStationTransfer(stationTransfer);
        }
        stationTransfer.setSn(0);
        return consignStationTransferMapper.insertOrUpdateStationTransfer(stationTransfer);
    }

    @Override
    public StationTransfer getLastTransferByOrderNo(String orderNo) {
        return consignStationTransferMapper.getLastTransferByOrderNo(orderNo);
    }
}
