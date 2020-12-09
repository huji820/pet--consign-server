package com.jxywkj.application.pet.model.consign;

import com.jxywkj.application.pet.model.common.Customer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author hj
 * @Date 2019-06-19 0:11
 * @Version 1.0
 */
@ApiModel(value = "商家员工")
@Data
public class Staff implements Serializable {

    private static final long serialVersionUID = -5676412775515337026L;
    @ApiModelProperty(value = "员工编号")
    Integer staffNo;

    @ApiModelProperty(value = "所属物流站")
    Station station;

    @ApiModelProperty(value = "员工性别")
    String sex;

    @ApiModelProperty(value = "员工名称")
    String staffName;

    @ApiModelProperty(value = "用户对象")
    Customer customer;

    @ApiModelProperty(value = "手机号码")
    String phone;

    @ApiModelProperty(value = "登录密码")
    String pwd;

    @ApiModelProperty(value = "员工状态:-1、淘汰;1、正常")
    short state;

    @ApiModelProperty(value = "职位: 售货员")
    Post post;

    @ApiModelProperty(value = "验证码")
    String verificationCode;

    @ApiModelProperty(value = "员工角色")
    Integer role;

    public Staff() {
    }

    public Staff(int staffNo, Station station, String staffName, Customer customer, String phone, String pwd, short state) {
        this.staffNo = staffNo;
        this.station = station;
        this.staffName = staffName;
        this.customer = customer;
        this.phone = phone;
        this.pwd = pwd;
        this.state = state;
    }

}
