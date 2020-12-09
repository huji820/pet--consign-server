package com.jxywkj.application.pet.model.business;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 站点余额流水
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className BusinessBalanceFlow
 * @date 2019/12/30 10:31
 **/
@Data
@ApiModel(description = "站点余额流水")
public class BusinessBalanceFlow {
    @ApiModelProperty(value = "流水编号")
    int flowNo;

    @ApiModelProperty(value = "所属商家")
    Business business;

    @ApiModelProperty(value = "流水时间")
    String dateTime;

    @ApiModelProperty(value = "流水时间")
    String flowType;

    @ApiModelProperty(value = "流水金额")
    BigDecimal flowAmount;

    @ApiModelProperty(value = "余额")
    BigDecimal balance;

    @ApiModelProperty(value = "单号")
    String billNo;

    @ApiModelProperty(value = "相关单号")
    String linkNo;
}
