package com.jxywkj.application.pet.model.consign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName RechargeOrder
 * @Description 用户充值
 * @Author LiuXiangLin
 * @Date 2019/8/19 11:13
 * @Version 1.0
 **/
@Data
@ApiModel(value = "用户充值")
public class RechargeOrder {
    @ApiModelProperty(value = "用户编号")
    String customerNo;

    @ApiModelProperty(value = "订单编号")
    String orderNo;

    @ApiModelProperty(value = "充值金额")
    BigDecimal rechargeAmount;

    @ApiModelProperty(value = "充值日期")
    String rechargeDate;

    @ApiModelProperty(value = "充值时间")
    String rechargeTime;

    @ApiModelProperty(value = "订单状态")
    String orderState;

    public RechargeOrder() {
    }

    public RechargeOrder(String customerNo, String orderNo, BigDecimal rechargeAmount, String rechargeDate, String rechargeTime, String orderState) {
        this.customerNo = customerNo;
        this.orderNo = orderNo;
        this.rechargeAmount = rechargeAmount;
        this.rechargeDate = rechargeDate;
        this.rechargeTime = rechargeTime;
        this.orderState = orderState;
    }
}
