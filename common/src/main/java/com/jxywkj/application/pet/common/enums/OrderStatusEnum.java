package com.jxywkj.application.pet.common.enums;

/**
 * @ClassName OrderStatusEnum
 * @Description 订单状态
 * @Author LiuXiangLin
 * @Date 2019/7/18 10:31
 * @Version 1.0
 **/
public enum OrderStatusEnum {
    /***/
    TO_BE_PAID("待付款", "订单生成成功，等待用户支付"),

    TO_BE_REVIEWED("待审核","已上传付款凭证，等待管理审核"),

    CANCEL("已取消", "用户取消了订单"),

    PAID("已支付", "订单已支付"),

    SHIPPED("待发货", "支付之后等待入港"),

    RECEIVING("待收货", "从起始站点出港之后"),

    COMPLETED("已完成", "订单已完成"),

    REFUND("已退款", "订单退款完成");

    /**
     * 状态
     */
    String state;
    /**
     * 描述
     */
    String describe;

    /**
     * 传入的参数
     */

    public OrderStatusEnum filter(String type) {
        if (TO_BE_PAID.getState().equals(type)) {
            return TO_BE_PAID;
        }
        if(TO_BE_REVIEWED.getState().equals(type)){
            return TO_BE_REVIEWED;
        }
        if (CANCEL.getState().equals(type)) {
            return CANCEL;
        }
        if (PAID.getState().equals(type)) {
            return PAID;
        }
        if (SHIPPED.getState().equals(type)) {
            return SHIPPED;
        }
        if (RECEIVING.getState().equals(type)) {
            return RECEIVING;
        }
        if (COMPLETED.getState().equals(type)) {
            return COMPLETED;
        }
        return null;
    }

    OrderStatusEnum(String state, String describe) {
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
