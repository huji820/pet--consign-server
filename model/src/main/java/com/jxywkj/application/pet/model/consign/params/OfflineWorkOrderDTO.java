package com.jxywkj.application.pet.model.consign.params;

import com.jxywkj.application.pet.model.consign.AddedInsure;
import com.jxywkj.application.pet.model.consign.AddedWeightCage;
import com.jxywkj.application.pet.model.consign.Station;
import com.yangwang.sysframework.veriflight.dto.PushFlightResponseData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName OfflineWorkOrderDTO
 * @Description
 * @Author GuoPengCheng
 * @Date 2020/7/17 10:51
 * @Version 1.0
 **/
@Data
@ApiModel(value = "线下生成工单Service参数对象")
public class OfflineWorkOrderDTO extends OrderDTO {
    @ApiModelProperty(value = "开始城市")
    String startCity;
    @ApiModelProperty(value = "出发站点")
    Station startStation;
    @ApiModelProperty(value = "目的城市")
    String endCity;
    @ApiModelProperty(value = "出发时间")
    String leaveDate;
    @ApiModelProperty(value = "发宠人姓名")
    String senderName;
    @ApiModelProperty(value = "发宠人电话")
    String senderPhone;
    @ApiModelProperty(value = "收宠人姓名")
    String receiverName;
    @ApiModelProperty(value = "收宠人电话")
    String receiverPhone;
    @ApiModelProperty(value = "customerNo")
    String customerNo;
    @ApiModelProperty(value = "宠物类型")
    String petClassify;
    @ApiModelProperty(value = "件数")
    int number;
    @ApiModelProperty(value = "宠物数量")
    int num;
    @ApiModelProperty(value = "宠物重量")
    BigDecimal weight;
    @ApiModelProperty(value = "运输方式")
    int transportType;
    @ApiModelProperty(value = "备注")
    String remarks;
    @ApiModelProperty(value = "订单价格")
    BigDecimal orderAmount;

    @ApiModelProperty(value = "合作价")
    BigDecimal joinOrderAmount;

    @ApiModelProperty(value = "客户价")
    BigDecimal retailOrderAmount;

    @ApiModelProperty(value = "差价")
    BigDecimal orderDifferentAmount;

}
