package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.StationTransfer;
import org.springframework.stereotype.Component;

/**
 * 中转站点
 */
@Component
public interface ConsignStationTransferMapper {

    /**
     * 添加或修改中转站点
     * @return
     */
    int insertOrUpdateStationTransfer(StationTransfer stationTransfer);

    /**
     * 获取订单最后一个中转站点
     * @param orderNo
     * @return
     */
    StationTransfer getLastTransferByOrderNo(String orderNo);
}
