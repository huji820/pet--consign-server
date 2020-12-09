package com.jxywkj.application.pet.model.consign.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className OrderAssignmentParam
 * @date 2020/3/17 8:48
 **/
@Data
@ApiModel(description = "订单分配分配参数对象")
public class OrderAssignmentParam {
    @ApiModelProperty(value = "用户编号")
    String customerNo;

    @ApiModelProperty(value = "订单编号")
    String orderNo;

    @ApiModelProperty(value = "员工编号列表")
    List<Integer> staffNoList;
}
