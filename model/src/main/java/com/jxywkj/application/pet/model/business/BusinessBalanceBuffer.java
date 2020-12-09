package com.jxywkj.application.pet.model.business;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 商家提现预扣表
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className BusinessBalanceBuffer
 * @date 2019/12/6 15:49
 **/
@Data
@ApiModel(description = "商家余额预扣表")
public class BusinessBalanceBuffer {
    @ApiModelProperty(value = "商家")
    Business business;

    @ApiModelProperty(value = "订单类型")
    String billType;

    @ApiModelProperty(value = "订单编号")
    String billNo;

    @ApiModelProperty(value = "金额")
    BigDecimal amount;
}
