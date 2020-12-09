package com.jxywkj.application.pet.model.consign.params;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LiuXiangLin
 * @version 1.0
 * @className StaffOrderQueryDTO
 * @description 员工订单查询对象
 * @date 2019/10/11 10:55
 **/
@Data
@Api(description = "工单查询参数对象")
public class StaffOrderQueryDTO {
    @ApiModelProperty(value = "员工编号")
    String staffNo;

    @ApiModelProperty(value = "订单编号")
    String orderNo;

    @ApiModelProperty(value = "下单开始时间")
    String startOrderTime;

    @ApiModelProperty(value = "下单结束时间")
    String endOrderTime;

    @ApiModelProperty(value = "出发开始时间")
    String startLeaveTime;

    @ApiModelProperty(value = "出发结束时间")
    String endLeaveTime;

    @ApiModelProperty(value = "站点编号")
    String stationNo;

    @ApiModelProperty(value = "订单类型数组")
    String[] orderTypeArray;

    @ApiModelProperty(value = "达到城市")
    String endCity;

    @ApiModelProperty(value = "寄件人或者发件人姓名")
    String name;

    @ApiModelProperty(value = "寄件人或者发件人电话")
    String phone;

    @ApiModelProperty(value = "航空公司二字码")
    String code;

    @ApiModelProperty(value = "排查条数")
    Integer offset;

    @ApiModelProperty(value = "查询条数")
    Integer limit;

}
