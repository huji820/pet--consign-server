package com.jxywkj.application.pet.model.consign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;


/**
 * <p>
 * 站点提现临时表
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className StationBalanceBuffer
 * @date 2019/12/6 14:55
 **/
@Data
@ApiModel(description = "站点临时减扣金额")
public class StationBalanceBuffer {
    @ApiModelProperty(value = "所属站点")
    Station station;

    @ApiModelProperty(value = "订单类型")
    String billType;

    @ApiModelProperty(value = "订单编号")
    String billNo;

    @ApiModelProperty(value = "金额")
    BigDecimal amount;
}
