package com.jxywkj.application.pet.common.enums;

/**
 * <p>
 * 移动app类型
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className AppTypeEnum
 * @date 2020/3/2 11:50
 **/
public enum AppTypeEnum {
    /**
     * iod
     */
    IOS("iOS", "APP"),
    /**
     * 安卓
     */
    ANDROID("Android", "APP"),
    /**
     * 微信小程序_运输
     */
    WE_APP_CONSIGN("WeappConsign", "JSAPI"),

    /**
     * 微信小程序_商城
     */
    WE_APP_MARKET("WeappMarket", "JSAPI");

    String type;
    String payType;

    AppTypeEnum(String type, String payType) {
        this.type = type;
        this.payType = payType;
    }

    public String getType() {
        return type;
    }

    public String getPayType() {
        return payType;
    }

    public static AppTypeEnum filter(String appType) {
        if (IOS.type.equals(appType)) {
            return IOS;
        }
        if (ANDROID.type.equals(appType)) {
            return ANDROID;
        }
        if (WE_APP_CONSIGN.type.equals(appType)) {
            return WE_APP_CONSIGN;
        }
        if (WE_APP_MARKET.type.equals(appType)) {
            return WE_APP_MARKET;
        }
        return null;
    }
}
