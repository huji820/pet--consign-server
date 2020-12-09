package com.jxywkj.application.pet.model.consign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 订单流水明细
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className OrderFlowDetail
 * @date 2020/5/19 14:37
 **/
@Data
@ApiModel(description = "订单流水明细")
public class OrderFlowDetail {
    @ApiModelProperty(value = "流水主键")
    String flowNo;

    @ApiModelProperty(value = "流水名称")
    String flowName;

    @ApiModelProperty(value = "流水类型")
    String flowType;

    @ApiModelProperty(value = "流水时间")
    String flowTime;

    @ApiModelProperty(value = "流水金额")
    String flowAmount;
}
