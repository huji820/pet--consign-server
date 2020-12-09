package com.jxywkj.application.pet.common.enums;

/**
 * <p>
 * 商家认证类型
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className AuthTypeEnum
 * @date 2020/3/14 16:51
 **/
public enum AuthTypeEnum {
    /**
     * 未认证
     */
    UN_AUTH(0),
    /**
     * 个人认证
     */
    INDIVIDUAL(1),
    /**
     * 商家认证
     */
    BUSINESS(2),
    /**
     * 平台人认证
     */
    PLATFORM(3);

    int type;

    AuthTypeEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
