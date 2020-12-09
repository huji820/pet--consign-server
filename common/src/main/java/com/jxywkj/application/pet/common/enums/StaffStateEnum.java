package com.jxywkj.application.pet.common.enums;

/**
 * @Author LiuXiangLin
 * @Description 员工状态枚举
 * @Date 8:59 2019/9/19
 * @Param
 * @return
 **/
public enum StaffStateEnum {
    /**
     * 正常 未审核 停用
     */
    NORMAL(Short.parseShort("1"), "正常"),
    UNAUDITED(Short.parseShort("0"), "待审核"),
    USELESS(Short.parseShort("-1"), "停用");

    short type;
    String describe;

    StaffStateEnum(short type, String describe) {
        this.type = type;
        this.describe = describe;
    }

    public short getType() {
        return type;
    }

    public String getDescribe() {
        return describe;
    }}
