package com.jxywkj.application.pet.model.common;

import com.jxywkj.application.pet.model.business.Business;
import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.model.consign.Station;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author Administrator
 * @Date 2019-06-19 9:28
 * @Version 1.0
 */
@Data
@ApiModel(description = "顾客表")
public class Customer {

    public Customer() {
    }

    @ApiModelProperty(value = "顾客编号")
    String customerNo;

    @ApiModelProperty(value = "顾客名称")
    String customerName;

    @ApiModelProperty(value = "unionId")
    String unionId;

    @ApiModelProperty(value = "openId")
    String openId;

    @ApiModelProperty(value = "头像")
    String headerImage;

    @ApiModelProperty(value = "手机号码")
    String phone;

    @ApiModelProperty(value = "最后登录时间")
    String lastLogintime;

    @ApiModelProperty(value = "注册日期")
    String registrationDate;

    @ApiModelProperty(value = "注册时间")
    String registrationTime;

    @ApiModelProperty(value = "性别")
    String sex;

    @ApiModelProperty(value = "积分")
    BigDecimal points;

    @ApiModelProperty(value = "余额")
    BigDecimal balance;

    @ApiModelProperty(value = "用户角色")
    Integer role;

    @ApiModelProperty(value = "员工对象")
    Staff staff;

    @ApiModelProperty(value = "商家对象")
    Business business;

    @ApiModelProperty(value = "所属分享商家")
    Business shareBusiness;

    @ApiModelProperty(value = "所属分享站点")
    Station shareStation;

    public Customer(String customerNo, String customerName, String headerImage, String phone, String sex) {
        this.customerNo = customerNo;
        this.customerName = customerName;
        this.headerImage = headerImage;
        this.phone = phone;
        this.sex = sex;
    }
}
