package com.jxywkj.application.pet.model.business;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author LiuXiangLin
 * @version 1.0
 * @className BusinessBalance
 * @description
 * @date 2019/10/12 10:28
 **/
@Data
@Api(description = "商家余额")
public class BusinessBalance {
    @ApiModelProperty(value = "商家编号")
    String businessNo;

    @ApiModelProperty(value = "返利总计金额")
    BigDecimal totalAmount;

    @ApiModelProperty(value = "最后返利金额")
    BigDecimal lastRebateAmount;

    @ApiModelProperty(value = "最后补价时间")
    String lastRebateTime;

    @ApiModelProperty(value = "最后提现金额")
    BigDecimal lastWithdrawAmount;

    @ApiModelProperty(value = "最后提现金额")
    String lastWithdrawTime;
}
