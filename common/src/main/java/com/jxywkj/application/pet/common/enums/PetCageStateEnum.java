package com.jxywkj.application.pet.common.enums;

/**
 * 笼具类型枚举
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className PetCageStateEnum
 * @date 2019/10/19 16:09
 **/
public enum PetCageStateEnum {
    /**
     * 可用的
     */
    USABLE(1),
    /**
     * 不可用的
     */
    UNAVAILABLE(0);

    int state;

    PetCageStateEnum(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }
}
