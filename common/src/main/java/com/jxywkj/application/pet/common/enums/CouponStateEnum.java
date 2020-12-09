package com.jxywkj.application.pet.common.enums;

/**
 * @ClassName CouponStateEnum
 * @Description 券状态枚举
 * @Author LiuXiangLin
 * @Date 2019/8/20 14:47
 * @Version 1.0
 **/
public enum CouponStateEnum {
    /***/
    available("未使用", "优惠券可以使用"),
    USED("已使用", "优惠券已经使用过"),
    expired("已过期", "优惠券已经过期");

    String sate;
    String describe;

    CouponStateEnum(String sate, String describe) {
        this.sate = sate;
        this.describe = describe;
    }

    public String getSate() {
        return sate;
    }

    public String getDescribe() {
        return describe;
    }}
