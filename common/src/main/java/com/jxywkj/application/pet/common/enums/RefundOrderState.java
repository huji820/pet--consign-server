package com.jxywkj.application.pet.common.enums;

/**
 * <p>
 * 退款单状态枚举
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className RefundOrderState
 * @date 2019/11/27 10:19
 **/
public enum RefundOrderState {
    /**
     * 退款中
     */
    REFUNDING("退款中", "退款中"),
    /**
     * 退款完成
     */
    REFUND_COMPLETE("退款完成", "退款完成"),
    /**
     * 取消退款
     */
    CANCEL_REFUND("取消退款", "取消退款");

    /**
     * 状态
     */
    String state;
    /**
     * 描述
     */
    String describe;

    RefundOrderState(String state, String describe) {
        this.state = state;
        this.describe = describe;
    }

    public String getState() {
        return state;
    }

    public String getDescribe() {
        return describe;
    }}
