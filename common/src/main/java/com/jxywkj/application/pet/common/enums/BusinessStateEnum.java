package com.jxywkj.application.pet.common.enums;

/**
 * @Author LiuXiangLin
 * @Description 商户状态枚举
 * @Date 11:28 2019/9/19
 * @Param
 * @return
 **/
public enum BusinessStateEnum {
    /**
     * 正常 未审核 正常
     */
    NORMAL(1, "正常"),
    UNAUDITED(0, "未审核"),
    USELESS(-1, "停用");


    private int type;
    private String describe;

    BusinessStateEnum(int type, String describe) {
        this.type = type;
        this.describe = describe;
    }

    public int getType() {
        return type;
    }

    public String getDescribe() {
        return describe;
    }}

