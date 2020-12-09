package com.jxywkj.application.pet.model.consign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 订单保险
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className ConsignInsureFlow
 * @date 2019/12/30 18:27
 **/
@Data
@ApiModel(description = "订单保险")
public class ConsignInsureFlow {
    @ApiModelProperty(value = "投保单号")
    String insureNo;

    @ApiModelProperty(value = "订单")
    Order order;

    @ApiModelProperty(value = "投保时间")
    String dateTime;

    @ApiModelProperty(value = "投保花费金额")
    BigDecimal spendAmount;

    @ApiModelProperty(value = "投保金额")
    BigDecimal insureAmount;
}
