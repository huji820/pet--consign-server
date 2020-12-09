package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.common.enums.OrderStatusEnum;
import com.jxywkj.application.pet.common.utils.CollectionUtils;
import com.jxywkj.application.pet.common.utils.DateUtilsExtend;
import com.jxywkj.application.pet.model.consign.Order;
import com.jxywkj.application.pet.service.facade.consign.ConsignOrderService;
import com.jxywkj.application.pet.service.facade.consign.ConsignOrderTaskService;
import com.jxywkj.application.pet.service.facade.consign.OrderCallBackService;
import com.yangwang.sysframework.utils.DateUtil;
import com.yangwang.sysframework.wechat.pay.WXPay;
import com.yangwang.sysframework.wechat.pay.WxPayType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className ConsignOrderTaskServiceImpl
 * @date 2019/11/21 15:11
 **/
@Service
public class ConsignOrderTaskServiceImpl implements ConsignOrderTaskService {
    /**
     * 每次处理的单据数量
     */
    private static final int EACH_ORDER_NUM = 50;

    /**
     * 支付成功状态
     */
    private static final String PAY_SUCCESS = "SUCCESS";

    @Resource
    ConsignOrderService consignOrderService;

    @Resource
    WXPay wxPay;

    @Resource
    OrderCallBackService orderCallBackService;

    @Override
    public void checkConsignOrder() {
        // 获取所有未支付的单子
        System.out.println("执行定时器");
        List<Order> unPayOrderList =
                consignOrderService.listOrderByTypeAndTime(OrderStatusEnum.TO_BE_PAID.getState(), DateUtil.formatFull(DateUtilsExtend.getAddMinDate(new Date(), 0 - 30)), 0, EACH_ORDER_NUM);

        if (!CollectionUtils.isEmpty(unPayOrderList)) {
            Map<String, String> orderStateMap;
            for (Order order : unPayOrderList) {
                // 获取支付状态
                try {
                    orderStateMap = wxPay.queryOrderState(order.getOutTradeNo(), WxPayType.WE_APP);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
                // 状态判断
                if (orderStateMap != null && !orderStateMap.isEmpty()) {
                    // 如果支付成功则回写状态
                    if (PAY_SUCCESS.equals(orderStateMap.get("trade_state"))) {
                        try {
                            orderCallBackService.payConsignOrder(order.getOrderNo());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void automaticConfirmConsignOrder() {
        // 获取未签收的订单
        List<String> unSignedOrderList =
                consignOrderService.listOutPortOvertime(0, EACH_ORDER_NUM);

        if (!CollectionUtils.isEmpty(unSignedOrderList)) {
            for (String orderNo : unSignedOrderList) {
                // 自动签收订单
                consignOrderService.automaticConfirmConsignOrder(orderNo);
            }
        }
    }

    @Override
    public void cancelUnpaidOrder() {
        // 获取未支付订单
        List<Order> unpaidOrderList = consignOrderService.listOrderByTypeAndTime(OrderStatusEnum.TO_BE_PAID.getState(), DateUtil.formatFull(DateUtil.getAddHourDate(new Date(), -24 * 2)), 0, EACH_ORDER_NUM);
        if (!CollectionUtils.isEmpty(unpaidOrderList)) {
            for (Order order : unpaidOrderList) {
                if (order != null) {
                    // 取消该订单
                    consignOrderService.updateOrderState(order.getOrderNo(), OrderStatusEnum.CANCEL.getState());
                }
            }
        }
    }
}
