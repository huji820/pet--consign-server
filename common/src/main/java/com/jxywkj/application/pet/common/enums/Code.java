package com.jxywkj.application.pet.common.enums;

import lombok.Getter;

/**
 * @author MiaoGuoZhu
 * @date 2018/5/24 15:19
 */
@Getter
public enum Code {
    /***/
    SUCCESS(200, "请求成功"),
    IS_NULL_DATA(300, "返回数据为空"),
    PARAM_ERROR(400, "请求参数有误"),
    ERROR_PHONE(400, "电话号码错误"),
    LOGIN_ERROR(401, "账号密码不能为空"),
    CHECK_ERROR(405, "校验错误"),
    NOT_EXISTS(406, "用户不存在"),
    ACCOUNT_DISABLED(410, "账号被禁用"),
    ACCOUNT_ERROR(420, "账号密码错误"),
    UPDATE_PASSWORD_ERROR(450, "原始密码错误"),
    NAME_ALREADY_EXISTS(460, "名称已存在"),
    ERROR(500, "系统异常"),
    INSERT_ERROR(501, "系统异常,录入失败"),
    UPDATE_ERROR(503, "系统异常,更新失败"),
    NOT_LOGIN(504, "请登录"),
    JSON_ERROR(600, "JSON 序列化错误"),
    TOKEN_ERROR(700, "TOKEN不合法"),
    TOKEN_FAIL(701, "TOKEN失效"),
    HAS_CHILD(801, "有子分类"),
    INVALID_TRANSPORT(901, "无效的运输路线");

    private int code;
    private String message;


    Code(int code, String message) {
        this.code = code;
        this.message = message;
    }

}