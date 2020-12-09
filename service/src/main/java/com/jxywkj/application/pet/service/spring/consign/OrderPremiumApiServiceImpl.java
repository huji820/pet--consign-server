package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.common.enums.AppTypeEnum;
import com.jxywkj.application.pet.common.enums.OrderStatusEnum;
import com.jxywkj.application.pet.common.enums.WeChatPayCallBackEnum;
import com.jxywkj.application.pet.common.utils.StringUtils;
import com.jxywkj.application.pet.dao.consign.OrderPremiumMapper;
import com.jxywkj.application.pet.model.common.CustomerOpenId;
import com.jxywkj.application.pet.model.consign.OrderPremium;
import com.jxywkj.application.pet.service.facade.common.CustomerMessageService;
import com.jxywkj.application.pet.service.facade.common.CustomerOpenIdService;
import com.jxywkj.application.pet.service.facade.consign.OrderPremiumApiService;
import com.yangwang.sysframework.utils.DateUtil;
import com.yangwang.sysframework.wechat.pay.WXPay;
import com.yangwang.sysframework.wechat.pay.WxPayBody;
import com.yangwang.sysframework.wechat.pay.WxPayType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * @author LiuXiangLin
 * @version 1.0
 * @className OrderPremiumApiServiceImpl
 * @description 订单补差价
 * @date 2019/10/10 15:17
 **/

@Service
public class OrderPremiumApiServiceImpl implements OrderPremiumApiService {
    @Resource
    OrderPremiumMapper orderPremiumMapper;

    @Resource
    CustomerMessageService customerMessageService;

    @Resource
    CustomerOpenIdService customerOpenIdService;

    @Resource
    WXPay wxPay;

    @Value("${localHost}")
    String localHost;

    @Override
    public int save(OrderPremium orderPremium) {
        // 设置必须参数
        orderPremium.setBillNo(getNewOrderNo());
        orderPremium.setOrderDate(DateUtil.format(new Date(), DateUtil.FORMAT_SIMPLE));
        orderPremium.setOrderTime(DateUtil.format(new Date(), DateUtil.FORMAT_TIME));
        orderPremium.setState(OrderStatusEnum.TO_BE_PAID.getState());

        // 存入本地
        int saveResult = orderPremiumMapper.saveOrderSpread(orderPremium);

        // 如果新增成功 则发送站内信
        if (saveResult > 0) {
            // 发送补价推送
            customerMessageService.sendOrderPremiumCustomerMessage(orderPremium);
        }

        return saveResult;
    }

    @Override
    public Map<String, String> getOrderPayParam(String billNo, String customerNo, String appType, String ipAddress) throws Exception {
        AppTypeEnum appTypeEnum = AppTypeEnum.filter(appType);
        if (appTypeEnum == null) {
            return null;
        }
        // 获取openid
        CustomerOpenId customerOpenId = customerOpenIdService.getByCustomerNoAndType(customerNo, appTypeEnum);
        if (customerOpenId == null) {
            return null;
        }
        // 获取补价单
        OrderPremium
                orderPremium = orderPremiumMapper.getByOrderNoAndOrderStatus(billNo, OrderStatusEnum.TO_BE_PAID.getState());

        if (orderPremium != null) {
            return wxPay.pay(new WxPayBody(customerOpenId.getOpenId(),
                    WeChatPayCallBackEnum.PREMIUM.getCompleteCallBackURL(localHost, billNo),
                    orderPremium.getBillNo(),
                    WeChatPayCallBackEnum.PREMIUM.getOrderDescribe(orderPremium.getBillNo()),
                    orderPremium.getAmount(),
                    ipAddress, WxPayType.filter(appTypeEnum.getPayType())));
        }

        return null;
    }

    @Override
    public OrderPremium getByBillNo(String billNo) {
        return orderPremiumMapper.getByBillNo(billNo);
    }

    @Override
    public int updateOrder2Paid(String billNo) {
        return orderPremiumMapper.updateOrderState(billNo, OrderStatusEnum.PAID.getState());
    }

    @Override
    public int countByOrderNoAndType(String orderNo, String billType) {
        return orderPremiumMapper.countByOrderNoAndType(orderNo, billType);
    }

    @Override
    public int cancelBill(String billNo) {
        return orderPremiumMapper.updateOrderState(billNo, OrderStatusEnum.CANCEL.getState());
    }

    /**
     * @param
     * @return java.lang.String
     * @author LiuXiangLin
     * @description 获取新的订单编号
     * @date 15:19 2019/10/10
     **/
    private String getNewOrderNo() {
        return StringUtils.getUuid();
    }
}
