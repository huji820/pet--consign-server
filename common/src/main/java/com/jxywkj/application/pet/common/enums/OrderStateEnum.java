package com.jxywkj.application.pet.common.enums;

/**
 * @ClassName OrderStateEnum
 * @Description 订单运输明细状态
 * @Author LiuXiangLin
 * @Date 2019/8/9 10:43
 * @Version 1.0
 **/
public enum OrderStateEnum {
    /***/
    TO_BE_PAID("待付款", "订单生成成功，等待用户支付"),
    PAID("已支付", "订单已支付"),
    TO_BE_PACK("待揽件", "等待揽件"),
    TO_BE_IN_PORT("待入港", "等待入港"),
    IN_PORT("已入港", "已经入港"),
    TO_BE_OUT_PORT("待出港", "等待出港"),
    OUT_PORT("已出港", "已经出港"),
    TO_BE_ARRIVED("待到达", "即将到达终点站点"),
    ARRIVED("已到达", "订单已经达到终点站点，等待派送或签收。"),
    DELIVERING("派送中", "订单派送中"),
    TO_BE_SIGN("待签收", "订单等待签收"),
    REFUND("已退款", "订单已经退款"),
    COMPLETED("已完成", "订单已经签收");

    /**
     * 类型
     */
    private String type;
    /**
     * 描述
     */
    private String description;

    OrderStateEnum(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
