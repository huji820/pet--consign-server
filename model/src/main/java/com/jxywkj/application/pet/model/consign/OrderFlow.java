package com.jxywkj.application.pet.model.consign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 订单流水
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className OrderFlow
 * @date 2020/5/19 14:28
 **/
@Data
@ApiModel(value = "订单流水")
public class OrderFlow {
    @ApiModelProperty(value = "订单")
    Order order;

    @ApiModelProperty(value = "订单流水明细列表")
    List<OrderFlowDetail> orderFlowDetails;
}
