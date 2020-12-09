package com.jxywkj.application.pet.model.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 用户openId
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className CustomerOpenId
 * @date 2020/3/4 9:58
 **/
@Data
@ApiModel(value = "用户openId")
public class CustomerOpenId {
    @ApiModelProperty(value = "用户对象")
    Customer customer;

    @ApiModelProperty(value = "app类型")
    String appType;

    @ApiModelProperty(value = "openid")
    String openId;

    public CustomerOpenId() {
    }

    public CustomerOpenId(Customer customer, String appType, String openId) {
        this.customer = customer;
        this.appType = appType;
        this.openId = openId;
    }
}
