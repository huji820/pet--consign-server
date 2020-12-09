package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.common.enums.BalanceFlowTypeEnum;
import com.jxywkj.application.pet.dao.consign.ConsignInsureFlowMapper;
import com.jxywkj.application.pet.model.consign.ConsignInsureFlow;
import com.jxywkj.application.pet.model.consign.Order;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.model.consign.StationBalance;
import com.jxywkj.application.pet.service.facade.consign.ConsignInsureFlowService;
import com.jxywkj.application.pet.service.facade.consign.StationBalanceFlowService;
import com.jxywkj.application.pet.service.facade.consign.StationBalanceService;
import com.jxywkj.application.pet.service.facade.consign.StationService;
import com.yangwang.sysframework.utils.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className ConsignInsureFlowServiceImpl
 * @date 2019/12/30 18:54
 **/
@Service
public class ConsignInsureFlowServiceImpl implements ConsignInsureFlowService {
    @Resource
    ConsignInsureFlowMapper consignInsureFlowMapper;

    @Resource
    StationBalanceService stationBalanceService;

    @Resource
    StationService stationService;

    @Override
    public int save(Order order) {
        if (order.getAddedInsure() != null
                && order.getAddedInsure().getInsureAmount() != null
                && order.getAddedInsure().getInsureAmount().compareTo(BigDecimal.ZERO) > 0) {

            ConsignInsureFlow consignInsureFlow = new ConsignInsureFlow();
            consignInsureFlow.setInsureNo(order.getOrderNo());
            consignInsureFlow.setDateTime(DateUtil.formatFull(new Date()));
            consignInsureFlow.setOrder(order);
            consignInsureFlow.setInsureAmount(order.getPetAmount());
            consignInsureFlow.setSpendAmount(order.getAddedInsure().getInsureAmount());

            //通过订单获取起始站点
            Station station = stationService.getByOrderNo(order.getOrderNo());
            // 修改站点余额
            stationBalanceService.saveStationRebateAmount(order, station, consignInsureFlow.getSpendAmount().negate(), BalanceFlowTypeEnum.CONSIGN_ORDER_INSURE);

            return consignInsureFlowMapper.save(consignInsureFlow);
        }
        return 0;
    }
}
