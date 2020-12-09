package com.jxywkj.application.pet.common.enums;

/**
 * @ClassName UserRolesEnum
 * @Description 用户角色枚举
 * @Author LiuXiangLin
 * @Date 2019/8/20 11:31
 * @Version 1.0
 **/
public enum UserRolesEnum {
    /**/
    ORDINARY_USERS(0, "普通用户"),
    STATION_ADMIN(1, "站点管理员"),
    CUSTOMER_SERVER(2, "客服"),
    DRIVER(3, "司机");

    int userRoles;
    String describe;

    UserRolesEnum(int userRoles, String describe) {
        this.userRoles = userRoles;
        this.describe = describe;
    }

    public int getUserRoles() {
        return userRoles;
    }

    public String getDescribe() {
        return describe;
    }}
