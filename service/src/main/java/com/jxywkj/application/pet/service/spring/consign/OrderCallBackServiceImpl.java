package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.common.enums.OrderStatusEnum;
import com.jxywkj.application.pet.common.enums.RechargeStateEnum;
import com.jxywkj.application.pet.model.consign.Order;
import com.jxywkj.application.pet.model.consign.OrderPremium;
import com.jxywkj.application.pet.model.consign.RechargeOrder;
import com.jxywkj.application.pet.service.facade.common.CustomerMessageService;
import com.jxywkj.application.pet.service.facade.common.CustomerService;
import com.jxywkj.application.pet.service.facade.common.SmsSendService;
import com.jxywkj.application.pet.service.facade.consign.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author LiuXiangLin
 * @version 1.0
 * @className OrderCallBackServiceImpl
 * @date 2019/10/31 10:41
 **/
@Service
public class OrderCallBackServiceImpl implements OrderCallBackService {
    @Resource
    ConsignOrderService consignOrderService;

    @Resource
    OrderStateService orderStateService;

    @Resource
    CustomerService customerService;

    @Resource
    RechargeOrderService rechargeOrderService;

    @Resource
    OrderPremiumApiService orderPremiumApiService;

    @Resource
    StationMessageService stationMessageService;

    @Resource
    CustomerMessageService customerMessageService;

    @Resource
    StationBalanceService stationBalanceService;

    @Resource
    SmsSendService smsSendService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int payConsignOrder(String orderNo) {
        // 获取订单
        Order order = consignOrderService.getConsignOrderByOrderNo(orderNo);

        // 如果订单是未支付状态则进行回写操作，防止微信多次回调导致数据错误
        if (order != null && (OrderStatusEnum.TO_BE_PAID.getState().equals(order.getState())
        ||OrderStatusEnum.TO_BE_REVIEWED.getState().equals(order.getState()))) {
            // 回写订单状态为待揽件
            consignOrderService.updateOrderState(order.getOrderNo(), OrderStatusEnum.SHIPPED.getState());

            // 添加流水
            orderStateService.addPayState(order.getOrderNo());

            // 给站点负责人发送站内信推送
            try {
                stationMessageService.saveAnOrderMessage(order);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 给收件人发件人发送站内信推送
            try {
                customerMessageService.saveAOrderCustomerMessage(order);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 给站点员工发送短信通知
            smsSendService.sendStationPlaceOrder(order);

            // 给下单人收件人发件人发送短信通知
            smsSendService.sendCustomerPlaceOrder(order);
        }
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int payRechargeOrder(String orderNo) {
        // 获取订单
        RechargeOrder rechargeOrder = rechargeOrderService.getByOrderNo(orderNo);

        // 如果订单未支付则进行回写操作 防止微信多次回调该地址导致数据错误
        if (rechargeOrder != null && RechargeStateEnum.UNPAID.getState().equals(rechargeOrder.getOrderState())) {
            // 回写订单状态
            rechargeOrderService.updateOrderSate(orderNo, RechargeStateEnum.APID.getState());

            // 添加余额
            customerService.rechargeCustomerBalance(orderNo);
        }
        // TODO 添加流水
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int payPremiumOrder(String billNo) {
        // 获取订单
        OrderPremium orderPremium = orderPremiumApiService.getByBillNo(billNo);

        // 如果订单处于未支付状态
        if (orderPremium != null && OrderStatusEnum.TO_BE_PAID.getState().equals(orderPremium.getState())) {
            // 回写订单状态
            orderPremiumApiService.updateOrder2Paid(billNo);

            // 通知站点相关相关负责人
            stationMessageService.saveOrderPremiumMessage(orderPremium);

            // 通知已经分配的人员
            customerMessageService.saveOrderPremiumMessage(orderPremium);

            // 如果是出港之后 则直接将补价金额返给站点
            stationBalanceService.saveOrderPremium(orderPremium);
        }
        return 0;
    }
}
