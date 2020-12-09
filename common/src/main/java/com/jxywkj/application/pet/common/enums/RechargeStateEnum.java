package com.jxywkj.application.pet.common.enums;

/**
 * @ClassName RechargeStateEnum
 * @Description 订单充值枚举
 * @Author LiuXiangLin
 * @Date 2019/8/20 9:56
 * @Version 1.0
 **/
public enum RechargeStateEnum {
    /***/
    UNPAID("未支付", "订单未支付"),
    APID("已支付", "订单完成支付");

    String state;
    String describe;

    RechargeStateEnum(String state, String describe) {
        this.state = state;
        this.describe = describe;
    }

    public String getState() {
        return state;
    }

    public String getDescribe() {
        return describe;
    }
}
