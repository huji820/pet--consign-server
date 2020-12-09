package com.jxywkj.application.pet.model.consign.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author huji
 * @Date 2019-06-19 0:14
 * @table t_consign_order
 * @Version 1.0
 */
@ApiModel(value = "订单表")
@Data
public class OrderVO {

    @ApiModelProperty(value = "订单编号")
    String orderNo;

    @ApiModelProperty(value = "运输线路名称")
    String transportName;

    @ApiModelProperty(value = "出发日期")
    String leaveDate;

    @ApiModelProperty(value = "下单日期")
    String orderDate;

    @ApiModelProperty(value = "下单时间")
    String orderTime;

    @ApiModelProperty(value = "宠物分类名称")
    String petSortName;

    @ApiModelProperty(value = "宠物类型名称")
    String petGenreName;

    @ApiModelProperty(value = "宠物重量")
    BigDecimal weight;

    @ApiModelProperty(value = "数量")
    int num;

    @ApiModelProperty(value = "成交的价格")
    BigDecimal finalRetailPrice;

    @ApiModelProperty(value = "商家的底价")
    BigDecimal finalJoinPrice;

    @ApiModelProperty("最终付款金额")
    BigDecimal paymentAmount;

    @ApiModelProperty("保价金额")
    BigDecimal insureAmount;

    @ApiModelProperty("宠物金额")
    BigDecimal petAmount;

    @ApiModelProperty(value = "寄件人姓名")
    String senderName;

    @ApiModelProperty(value = "寄件人联系电话")
    String senderPhone;

    @ApiModelProperty(value = "收件人姓名")
    String receiverName;

    @ApiModelProperty(value = "收件人电话")
    String receiverPhone;

    @ApiModelProperty(value = "备注")
    String remarks;

    @ApiModelProperty(value = "状态")
    String state;

    @ApiModelProperty("customerNo")
    String customerNo;

    @ApiModelProperty(value = "宠物年龄")
    String petAge;

    @ApiModelProperty(value = "推荐人名字")
    String recommendName;

    @ApiModelProperty(value = "推荐人电话")
    String recommendPhone;

    @ApiModelProperty(value = "推荐人备注信息")
    String recommendRemarks;

    @ApiModelProperty(value = "托运站名称")
    String stationName;

    @ApiModelProperty(value = "线下支付标识 1为线下支付")
    String offlinePaymentLogo;

    public OrderVO() {
    }

}
