package com.jxywkj.application.pet.model.consign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName OrderTransport
 * @Description 订单运输
 * @Author LiuXiangLin
 * @Date 2019/9/23 13:58
 * @Version 1.0
 **/
@ApiModel(description = "订单运输")
@Data
public class OrderTransport {
    @ApiModelProperty(value = "所属订单")
    Order order;

    @ApiModelProperty(value = "所属站点")
    Station station;

    @ApiModelProperty(value = "员工")
    Staff staff;

    @ApiModelProperty(value = "运输方式")
    int transportType;

    @ApiModelProperty(value = "车次号（航班号，火车车次，大巴车次）")
    String transportNum;

    @ApiModelProperty("快递单号")
    String expressNum;

    @ApiModelProperty(value = "出发城市（如果是航班则填写出发地三字码）")
    String startCity;

    @ApiModelProperty(value = "目的城市（如果是航班则填写目的城市的三字码）")
    String endCity;

    @ApiModelProperty(value = "预计出发时间")
    String dateTime;

}
