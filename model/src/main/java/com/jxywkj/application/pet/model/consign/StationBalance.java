package com.jxywkj.application.pet.model.consign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName StationBalance
 * @Description 站点返利汇总
 * @Author LiuXiangLin
 * @Date 2019/8/26 13:53
 * @Version 1.0
 **/
@Data
@ApiModel("站点余额")
public class StationBalance {
    @ApiModelProperty("站点")
    Station station;

    @ApiModelProperty("总计金额")
    BigDecimal totalAmount;

    @ApiModelProperty("最近一单获取金额")
    BigDecimal lastRebateAmount;

    @ApiModelProperty("最后一单返利时间")
    String lastRebateTime;

    @ApiModelProperty("最后提现金额")
    BigDecimal lastWithdrawAmount;

    @ApiModelProperty("最后提现时间")
    String lastWithdrawTime;
}
