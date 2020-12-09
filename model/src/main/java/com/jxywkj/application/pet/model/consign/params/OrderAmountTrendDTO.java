package com.jxywkj.application.pet.model.consign.params;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className OrderAmountTrendDTO
 * @date 2020/1/6 15:23
 **/
@Data
@ApiModel(description = "订单进去向查询参数")
public class OrderAmountTrendDTO {
    @ApiModelProperty(value = "站点编号")
    Integer stationNo;

    @ApiModelProperty(value = "订单编号")
    String orderNo;

    @ApiModelProperty(value = "订单状态")
    String orderState;

    @ApiModelProperty(value = "发件人号码")
    String senderPhone;

    @ApiModelProperty(value = "收件人号码")
    String receiverPhone;

    @ApiModelProperty(value = "排除条数")
    Integer offset;

    @ApiModelProperty(value = "显示条数")
    Integer limit;
}
