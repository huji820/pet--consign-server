package com.jxywkj.application.pet.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 订单查询dto
 * @Author zxj
 * @Date 2020/10/14 16:18
 * @Version 1.0
 */
@Data
public class OrderQueryDto {

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("站点编号")
    private Integer stationNo;

    @ApiModelProperty("城市名称")
    private String cityName;

    @ApiModelProperty("出港开始时间")
    private String leaveStartDate;

    @ApiModelProperty("出港结束时间")
    private String leaveEndDate;

    @ApiModelProperty("下单开始时间")
    private String orderStartDate;

    @ApiModelProperty("下单结束时间")
    private String orderEndDate;

    @ApiModelProperty("开始条数")
    private Integer start;

    @ApiModelProperty("每页条数")
    private Integer limit;
}
