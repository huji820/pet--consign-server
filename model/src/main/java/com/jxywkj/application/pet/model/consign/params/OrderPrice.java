package com.jxywkj.application.pet.model.consign.params;

import com.jxywkj.application.pet.model.consign.AddedInsure;
import com.jxywkj.application.pet.model.consign.AddedWeightCage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单价格
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className OrderPrice
 * @date 2019/10/21 18:24
 **/
@Data
@ApiModel(description = "订单价格")
public class OrderPrice {
    @ApiModelProperty(value = "订单价格")
    BigDecimal orderAmount;

    @ApiModelProperty(value = "合作价")
    BigDecimal joinOrderAmount;

    @ApiModelProperty(value = "客户价")
    BigDecimal retailOrderAmount;

    @ApiModelProperty(value = "差价")
    BigDecimal orderDifferentAmount;

    @ApiModelProperty(value = "送宠上门金额")
    AddedOnDoorAmount sendAmount;

    @ApiModelProperty(value = "上门接宠金额")
    AddedOnDoorAmount receiptAmount;

    @ApiModelProperty(value = "所选笼具")
    AddedWeightCage addedWeightCage;

    @ApiModelProperty(value = "保价")
    AddedInsure addedInsure;

    public OrderPrice() {
    }

    public OrderPrice(BigDecimal orderAmount, BigDecimal orderDifferentAmount, AddedWeightCage addedWeightCage, AddedInsure addedInsure) {
        this.orderAmount = orderAmount;
        this.orderDifferentAmount = orderDifferentAmount;
        this.addedWeightCage = addedWeightCage;
        this.addedInsure = addedInsure;
    }

    public static OrderPrice newZeroInstance() {
        OrderPrice result = new OrderPrice();
        result.setOrderAmount(BigDecimal.ZERO);
        result.setOrderDifferentAmount(BigDecimal.ZERO);
        result.setAddedWeightCage(null);
        result.setAddedInsure(null);
        result.setJoinOrderAmount(BigDecimal.ZERO);
        result.setRetailOrderAmount(BigDecimal.ZERO);
        return result;
    }
}
