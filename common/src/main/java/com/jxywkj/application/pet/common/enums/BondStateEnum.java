package com.jxywkj.application.pet.common.enums;

/**
 * <p>
 * 缴纳保证金状态
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className BondStateEnum
 * @date 2020/3/16 13:55
 **/
public enum BondStateEnum {
    /**
     * 未支付
     */
    UNPAID(0),
    /**
     * 已经支付
     */
    PAID(1);

    int state;

    BondStateEnum(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }}
