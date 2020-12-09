package com.jxywkj.application.pet.model.consign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName OrderAssignment
 * @Description 订单分配
 * @Author LiuXiangLin
 * @Date 2019/8/24 14:16
 * @Version 1.0
 **/
@Data
@ApiModel(value = "订单分配")
public class OrderAssignment {

    @ApiModelProperty(value = "主键（自增）")
    int assignmentNo;

    @ApiModelProperty(value = "订单单号")
    Order order;

    @ApiModelProperty(value = "分配时间")
    String assignmentTime;

    @ApiModelProperty(value = "操作人")
    Staff operator;

    @ApiModelProperty(value = "员工")
    Staff staff;

    @ApiModelProperty(value = "所属员工")
    List<Staff> staffList;
}
