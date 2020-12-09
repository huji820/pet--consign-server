package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.common.enums.BalanceFlowTypeEnum;
import com.jxywkj.application.pet.dao.consign.StationBalanceMapper;
import com.jxywkj.application.pet.model.consign.*;
import com.jxywkj.application.pet.service.facade.consign.*;
import com.yangwang.sysframework.utils.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName StationBalanceServiceImpl
 * @Description 站点返利
 * @Author LiuXiangLin
 * @Date 2019/8/26 14:37
 * @Version 1.0
 **/
@Service
public class StationBalanceServiceImpl implements StationBalanceService {
    @Resource
    StationBalanceMapper stationBalanceMapper;

    @Resource
    StationService stationService;

    @Resource
    ConsignOrderService consignOrderService;

    @Resource
    StationMessageService stationMessageService;

    @Resource
    StationBalanceFlowService stationBalanceFlowService;

    @Resource
    ConsignInsureFlowService consignInsureFlowService;

    @Override
    public int subtractTotalAmountByWithdraw(String stationNo, BigDecimal subtractAmount) {
        return stationBalanceMapper.subtractTotalAmountByWithdraw(stationNo, subtractAmount);
    }

    @Override
    public BigDecimal getTotalByStationNo(int stationNo) {
        return stationBalanceMapper.getTotalByStationNo(stationNo);
    }

    @Override
    public int saveOrderRebate(Order order) {
        // 获取站点
        //Station station = stationService.getStation(order.getTransport().getStation().getStationNo());
        Station station = stationService.getByOrderNo(order.getOrderNo());
        // 修改站点余额
        BigDecimal rebateAmount = order.getPaymentAmount();
        stationBalanceMapper.saveOrUpdateRebate(getBaseStationRebate(station, rebateAmount));

        // 余额流水
        stationBalanceFlowService.save(station, rebateAmount, order.getOrderNo(), null, BalanceFlowTypeEnum.CONSING_ORDER_STATION);

        return 1;
    }


    @Override
    public int saveStationRebateAmount(Order order, Station station, BigDecimal amount, BalanceFlowTypeEnum balanceFlowTypeEnum) {
        // 获取返利对象
        StationBalance stationBalance = getBaseStationRebate(station, amount);

        // 返利
        int result = stationBalanceMapper.saveOrUpdateRebate(stationBalance);

        // 余额流水
        result += stationBalanceFlowService.save(station, amount, order.getOrderNo(), null, balanceFlowTypeEnum);

        // 站内信提醒
        result += stationMessageService.saveRebateMessage(order);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveOrderPremium(OrderPremium orderPremium) {
        // 修改站点余额
        StationBalance stationBalance = getBaseStationRebate(orderPremium.getStaff().getStation(), orderPremium.getAmount());
        stationBalanceMapper.saveOrUpdateRebate(stationBalance);

        // 添加余额流水
        stationBalanceFlowService.save(orderPremium.getStaff().getStation(), orderPremium.getAmount(), orderPremium.getBillNo(), orderPremium.getOrderNo(), BalanceFlowTypeEnum.CONSING_ORDER_PREMIUM);

        return 0;
    }

    @Override
    public int saveRefundOrder(Order order, Station station, BigDecimal refundAmount) {
        StationBalance stationBalance = getBaseStationRebate(station, refundAmount);

        // 修改站点余额
        stationBalanceMapper.saveOrUpdateRebate(stationBalance);

        // 余额流水
        stationBalanceFlowService.save(station, refundAmount, order.getOrderNo(), null, BalanceFlowTypeEnum.CONSIGN_ORDER_REFUND);

        return 1;
    }


    /**
     * @param station , rebateAmount
     * @return com.jxywkj.application.pet.model.consign.StationBalance
     * @author LiuXiangLin
     * @description 获取订单基本对象
     * @date 17:55 2019/10/12
     **/
    private StationBalance getBaseStationRebate(Station station, BigDecimal rebateAmount) {
        StationBalance result = new StationBalance();

        // 设置属性
        result.setStation(station);
        result.setLastRebateAmount(rebateAmount);
        result.setLastRebateTime(DateUtil.format(new Date(), DateUtil.FORMAT_FULL));

        return result;
    }

}
