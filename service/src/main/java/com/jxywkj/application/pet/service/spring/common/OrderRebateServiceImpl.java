package com.jxywkj.application.pet.service.spring.common;

import com.jxywkj.application.pet.common.enums.BalanceFlowTypeEnum;
import com.jxywkj.application.pet.common.enums.BusinessStateEnum;
import com.jxywkj.application.pet.common.utils.BigDecimalUtils;
import com.jxywkj.application.pet.model.business.Business;
import com.jxywkj.application.pet.model.common.Customer;
import com.jxywkj.application.pet.model.consign.Order;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.service.facade.business.BusinessBalanceService;
import com.jxywkj.application.pet.service.facade.business.BusinessService;
import com.jxywkj.application.pet.service.facade.common.CustomerService;
import com.jxywkj.application.pet.service.facade.common.OrderRebateService;
import com.jxywkj.application.pet.service.facade.consign.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author LiuXiangLin
 * @version 1.0
 * @className OrderRebateServiceImpl
 * @description
 * @date 2019/10/12 17:26
 **/

@Service
public class OrderRebateServiceImpl implements OrderRebateService {
    @Resource
    ConsignOrderService consignOrderService;

    @Resource
    StationService stationService;

    @Resource
    StationBalanceService stationBalanceService;

    @Resource
    BusinessService businessService;

    @Resource
    BusinessBalanceService businessBalanceService;

    @Resource
    CustomerService customerService;

    @Override
    public int saveRebate(String orderNo) {
        /*
         * 1、如果有分享人 而且分享人是站点员工或者商家 则将一部分差价给到分享站点或者商家账户
         * 2、出发站点的金额为 = 订单总金额 - 分享人获得金额
         *
         * */
        // 获取订单
        Order order = consignOrderService.getConsignOrderByOrderNo(orderNo);

        //通过订单获取站点
        Station station = stationService.getByOrderNo(orderNo);

        // 如果订单存在而且有差价
        if (order != null) {
            // 获取用户对象
            Customer customer = customerService.getCustomerByCustomerNo(order.getCustomerNo());

            // 如果用户存在 并且有分享人 以及存在差价
            if (customer != null && triggerRebate(order, customer)) {
                // 返利金额
                BigDecimal rebateAmount = order.getPaymentAmount().compareTo(order.getFinalRetailPrice()) > 0 ?
                        order.getFinalRetailPrice().subtract(order.getFinalJoinPrice())
                        : order.getPaymentAmount().subtract(order.getFinalJoinPrice());

                // 出发站点的金额
                BigDecimal stationAmount = order.getPaymentAmount().subtract(rebateAmount);

                // 将最低价返给站点
                stationBalanceService.saveStationRebateAmount(order, station, stationAmount, BalanceFlowTypeEnum.CONSING_ORDER_STATION);

                if (customer.getShareStation() != null) {
                    //  如果存在分享站点

                    // 通过open查询是否是站点
                    Station shareStation = stationService.getStation(customer.getShareStation().getStationNo());
                    // 如果是站点员工 则将金额返给指定的商家
                    if (shareStation != null) {
                        return stationBalanceService.saveStationRebateAmount(order, shareStation, rebateAmount, BalanceFlowTypeEnum.CONSING_ORDER_REBATE);

                    }
                }

                if (customer.getShareBusiness() != null) {
                    // 如果存在分享商家

                    // 通过openId查询是否是商家
                    Business shareBusiness = businessService.getByBusinessNo(customer.getShareBusiness().getBusinessNo(), BusinessStateEnum.NORMAL);
                    if (shareBusiness != null) {
                        return businessBalanceService.saveRebateAmount(order, shareBusiness, rebateAmount);
                    }
                }

                // 说明分享人是无效的 将返现金额给出发站点 将所有的金额都返给站点
                return stationBalanceService.saveStationRebateAmount(order, station, rebateAmount, BalanceFlowTypeEnum.CONSING_ORDER_REBATE);
            } else {
                // 没有用户 没有差价 没有分享人(将所有金额都返利给站点) 或者分享人无效
                return stationBalanceService.saveOrderRebate(order);
            }
        }

        return 0;
    }


    /**
     * 是否触发返利
     *
     * @param order    订单
     * @param customer 用户
     * @return boolean
     * @author LiuXiangLin
     * @date 17:23 2019/10/25
     **/
    private boolean triggerRebate(Order order, Customer customer) {
        // 如果支付金额大于最低价才可以触发返利
        if (order.getPaymentAmount().compareTo(order.getFinalJoinPrice()) > 0) {
            // 有商家分享
            if (customer.getShareBusiness() != null) {
                return businessService.getByBusinessNo(customer.getShareBusiness().getBusinessNo(),BusinessStateEnum.NORMAL) != null;
            }
            // 有站点分享
            if (customer.getShareStation() != null) {
                return stationService.getStation(customer.getShareStation().getStationNo()) != null;
            }
        }
        return false;
    }
}
