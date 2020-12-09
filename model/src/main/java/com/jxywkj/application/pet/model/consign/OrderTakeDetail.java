package com.jxywkj.application.pet.model.consign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 提货详情
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className OrderTakeDetail
 * @date 2020/4/8 9:18
 **/
@Data
@ApiModel(description = "提货详情")
public class OrderTakeDetail extends TransportTakeDetail {
    @ApiModelProperty(value = "提货详情编号")
    Long takeDetailNo;

    @ApiModelProperty(value = "订单")
    Order order;
}
