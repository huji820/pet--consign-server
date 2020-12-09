package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.common.enums.AppTypeEnum;
import com.jxywkj.application.pet.common.enums.RechargeStateEnum;
import com.jxywkj.application.pet.common.enums.WeChatPayCallBackEnum;
import com.jxywkj.application.pet.common.utils.StringUtils;
import com.jxywkj.application.pet.dao.consign.RechargeOrderMapper;
import com.jxywkj.application.pet.model.common.Customer;
import com.jxywkj.application.pet.model.common.CustomerOpenId;
import com.jxywkj.application.pet.model.consign.RechargeOrder;
import com.jxywkj.application.pet.service.facade.common.CustomerOpenIdService;
import com.jxywkj.application.pet.service.facade.common.CustomerService;
import com.jxywkj.application.pet.service.facade.consign.RechargeOrderService;
import com.yangwang.sysframework.utils.DateUtil;
import com.yangwang.sysframework.wechat.pay.WXPay;
import com.yangwang.sysframework.wechat.pay.WxPayBody;
import com.yangwang.sysframework.wechat.pay.WxPayType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @ClassName RechargeOrderServiceImpl
 * @Description 用户充值
 * @Author LiuXiangLin
 * @Date 2019/8/19 13:39
 * @Version 1.0
 **/
@Service
public class RechargeOrderServiceImpl implements RechargeOrderService {
    @Resource
    CustomerService customerService;

    @Resource
    RechargeOrderMapper rechargeOrderMapper;

    @Resource
    CustomerOpenIdService customerOpenIdService;

    @Resource
    WXPay wxPay;

    @Value("${localHost}")
    String localHost;

    @Override
    public Map<String, String> addRechargeOrder(String customerNo, BigDecimal rechargeAmount, String appType, String userAddress) throws Exception {
        AppTypeEnum appTypeEnum = AppTypeEnum.filter(appType);
        if (appTypeEnum == null) {
            return null;
        }
        // 获取用户openId
        CustomerOpenId customerOpenId = customerOpenIdService.getByCustomerNoAndType(customerNo, appTypeEnum);
        if (customerOpenId == null) {
            return null;
        }
        // 获取用户信息
        Customer customer = customerService.getCustomerByCustomerNo(customerNo);
        // 获取订单
        RechargeOrder rechargeOrder = getRechargeOrder(customer.getCustomerNo(), rechargeAmount, RechargeStateEnum.UNPAID);
        // 插入订单
        int addResult = rechargeOrderMapper.addRechargeOrder(rechargeOrder);
        // 获取支付参数
        if (addResult > 0) {
            return wxPay.pay(new WxPayBody(customerOpenId.getOpenId(),
                    WeChatPayCallBackEnum.RECHARGE.getCompleteCallBackURL(localHost, rechargeOrder.getOrderNo()),
                    rechargeOrder.getOrderNo(), WeChatPayCallBackEnum.RECHARGE.getOrderDescribe(rechargeOrder.getOrderNo()),
                    rechargeOrder.getRechargeAmount(), userAddress, WxPayType.filter(appTypeEnum.getPayType())));
        }
        return null;
    }

    @Override
    public RechargeOrder getByOrderNo(String orderNo) {
        return rechargeOrderMapper.getByOrderNo(orderNo);
    }

    @Override
    public int updateOrderSate(String orderNo, String orderType) {
        return rechargeOrderMapper.updateOrderState(orderNo, orderType);
    }

    private RechargeOrder getRechargeOrder(String customerNo, BigDecimal rechargeAmount, RechargeStateEnum rechargeStateEnum) {
        Date now = new Date();
        return new RechargeOrder(customerNo, getRechargeOderNo(), rechargeAmount, DateUtil.format(now, DateUtil.FORMAT_SIMPLE), DateUtil.format(now, DateUtil.FORMAT_TIME), rechargeStateEnum.getState());
    }

    private String getRechargeOderNo() {
        return StringUtils.getUuid();
    }

}
