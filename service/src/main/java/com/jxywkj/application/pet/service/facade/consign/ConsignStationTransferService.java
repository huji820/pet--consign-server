package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.StationTransfer;
import org.springframework.stereotype.Service;

/**
 * 中转站点
 */

public interface ConsignStationTransferService {

    /**
     * 添加或修改中转站点
     * @return
     */
    int insertOrUpdateStationTransfer(String orderNo, String stationNo);

    /**
     * 获取订单最后一个中转站点
     * @param orderNo
     * @return
     */
    StationTransfer getLastTransferByOrderNo(String orderNo);
}
