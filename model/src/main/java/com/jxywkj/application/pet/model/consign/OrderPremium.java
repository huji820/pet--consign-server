package com.jxywkj.application.pet.model.consign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;


/**
 * @Author LiuXiangLin
 * @Description 补差价订单
 * @Date 9:14 2019/8/27
 * @Param
 * @return
 **/
@ApiModel(value = "订单补差价表")
@Data
public class OrderPremium {
    @ApiModelProperty(value = "补差价编号")
    String billNo;

    @ApiModelProperty(value = "原单据编号")
    String orderNo;

    @ApiModelProperty(value = "订单日期")
    String orderDate;

    @ApiModelProperty(value = "订单时间")
    String orderTime;

    @ApiModelProperty(value = "补差价原因")
    String reason;

    @ApiModelProperty(value = "操作人")
    Staff staff;

    @ApiModelProperty(value = "图片地址")
    String appendImages;

    @ApiModelProperty(value = "状态")
    String state;

    @ApiModelProperty(value = "差价")
    BigDecimal amount;

}
