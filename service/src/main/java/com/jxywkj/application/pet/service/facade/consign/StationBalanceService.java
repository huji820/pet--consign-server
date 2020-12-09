package com.jxywkj.application.pet.service.facade.consign;


import com.jxywkj.application.pet.common.enums.BalanceFlowTypeEnum;
import com.jxywkj.application.pet.model.consign.Order;
import com.jxywkj.application.pet.model.consign.OrderPremium;
import com.jxywkj.application.pet.model.consign.Station;

import java.math.BigDecimal;

/**
 * @ClassName StationBalanceService
 * @Description 商家返利
 * @Author LiuXiangLin
 * @Date 2019/8/26 14:36
 * @Version 1.0
 **/
public interface StationBalanceService {

    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 减去商家站点指定的金额(商家提现)
     * @Date 15:28 2019/8/26
     * @Param [stationNo, subtractAmount]
     **/
    int subtractTotalAmountByWithdraw(String stationNo, BigDecimal subtractAmount);

    /**
     * @return java.math.BigDecimal
     * @Author LiuXiangLin
     * @Description 获取汇总数据
     * @Date 14:03 2019/9/30
     * @Param [stationNo]
     **/
    BigDecimal getTotalByStationNo(int stationNo);

    /**
     * @param order 订单
     * @return int
     * @author LiuXiangLin
     * @description 将订单的所有金额返给站点
     * @date 17:59 2019/10/12
     **/
    int saveOrderRebate(Order order);

    /**
     * 添加返利
     *
     * @param order               订单
     * @param station             站点
     * @param amount              返利金额
     * @param balanceFlowTypeEnum 流水类型
     * @return int
     * @author LiuXiangLin
     * @date 17:33 2019/10/25
     **/
    int saveStationRebateAmount(Order order, Station station, BigDecimal amount, BalanceFlowTypeEnum balanceFlowTypeEnum);

    /**
     * 补价单返利
     *
     * @param orderPremium 补价单
     * @return int
     * @author LiuXiangLin
     * @date 16:03 2019/10/26
     **/
    int saveOrderPremium(OrderPremium orderPremium);

    /**
     * <p>
     * 退款触发返利
     * </p>
     *
     * @param order        订单编号
     * @param station      所属站点
     * @param refundAmount 退款金额
     * @return int
     * @author LiuXiangLin
     * @date 14:05 2019/11/27
     **/
    int saveRefundOrder(Order order, Station station, BigDecimal refundAmount);

}
