package com.jxywkj.application.pet.model.consign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName OrderTempDeliver
 * @Description
 * @Author LiuXiangLin
 * @Date 2019/9/25 9:44
 * @Version 1.0
 **/
@ApiModel(description = "订单临时派送表")
@Data
public class OrderTempDeliver {
    @ApiModelProperty(value = "派送单编号（自增）")
    int tempDeliverNo;

    @ApiModelProperty(value = "订单")
    Order order;

    @ApiModelProperty(value = "所属站点")
    Station station;

    @ApiModelProperty(value = "收件人姓名")
    String recipientName;

    @ApiModelProperty(value = "收件人电话")
    String recipientPhone;

    @ApiModelProperty(value = "派送地址")
    String address;

    @ApiModelProperty(value = "经度")
    double longitude;

    @ApiModelProperty(value = "纬度")
    double latitude;

    @ApiModelProperty(value = "派送时间")
    String deliverTime;
}
