package com.jxywkj.application.pet.common.enums;

public enum MessageTypeEnum {
    /***/
    UN_READ("未读", "未读"),
    READ("已读", "已读"),
    DELETE("删除", "删除");

    private String describe;
    String state;

    MessageTypeEnum(String describe, String state) {
        this.describe = describe;
        this.state = state;
    }

    public String getDescribe() {
        return describe;
    }

    public String getState() {
        return state;
    }
}
