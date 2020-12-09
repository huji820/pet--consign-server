package com.jxywkj.application.pet.model.consign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName OrderRemarks
 * @Description 订单额外备注
 * @Author LiuXiangLin
 * @Date 2019/9/23 10:06
 * @Version 1.0
 **/
@Data
@ApiModel(description = "订单备注")
public class OrderRemarks {
    @ApiModelProperty(value = "编号(自增)")
    int remarksNo;

    @ApiModelProperty(value = "单号")
    Order order;

    @ApiModelProperty(value = "所属站点")
    Station station;

    @ApiModelProperty(value = "编写人")
    Staff staff;

    @ApiModelProperty(value = "备注")
    String remarks;

    @ApiModelProperty(value = "添加时间")
    String dateTime;
}
