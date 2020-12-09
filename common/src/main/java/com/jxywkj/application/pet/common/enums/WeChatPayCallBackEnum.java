package com.jxywkj.application.pet.common.enums;

/**
 * @Author LiuXiangLin
 * @Description 微信回调枚举
 * @Date 10:01 2019/8/19
 * @Param
 * @return
 **/
public enum WeChatPayCallBackEnum {
    /***/
    ORDER_PAY("/pay/callback/transport/", "支付订单"),
    RECHARGE("/pay/callback/recharge/", "充值"),
    PREMIUM("/pay/callback/premium/", "补价单");

    String callBackURL;
    String paramBody;

    WeChatPayCallBackEnum(String callBackURL, String paramBody) {
        this.callBackURL = callBackURL;
        this.paramBody = paramBody;
    }

    public String getCallBackURL() {
        return callBackURL;
    }

    public String getParamBody() {
        return paramBody;
    }

    public String getCompleteCallBackURL(String localHost, String orderNo) {
        return localHost + this.getCallBackURL() + orderNo;
    }

    public String getOrderDescribe(String orderNo) {
        return getParamBody();
    }
}
