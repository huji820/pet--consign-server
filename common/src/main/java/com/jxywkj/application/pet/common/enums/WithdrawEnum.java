package com.jxywkj.application.pet.common.enums;

/**
 * @ClassName WithdrawEnum
 * @Description 站点提现枚举
 * @Author LiuXiangLin
 * @Date 2019/8/26 15:06
 * @Version 1.0
 **/
public enum WithdrawEnum {
    /***/
    TO_BE_AUDITED("待审核", "待审核"),
    REJECT("已驳回", "已驳回"),
    COMPLETED("已完成", "已完成");

    String state;
    String describe;

    WithdrawEnum(String state, String describe) {
        this.state = state;
        this.describe = describe;
    }

    public String getState() {
        return state;
    }

    public String getDescribe() {
        return describe;
    }}
