package com.jxywkj.application.pet.model.consign;

import com.jxywkj.application.pet.common.annotation.Excel;
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
 * @className StationBalanceFlow
 * @date 2019/12/28 15:49
 **/
@Data
@ApiModel(description = "站点余额流水")
public class StationBalanceFlow {
    @ApiModelProperty(value = "流水单号")
    @Excel(name = "流水单号", index = 1)
    int flowNo;

    @ApiModelProperty(value = "站点")
    Station station;

    @ApiModelProperty(value = "流水时间")
    @Excel(name = "流水时间", index = 2)
    String dateTime;

    @ApiModelProperty(value = "流水类型")
    String flowType;

    @ApiModelProperty(value = "流水金额")
    @Excel(name = "流水金额", index = 3)
    BigDecimal flowAmount;

    @ApiModelProperty(value = "余额")
    @Excel(name = "余额", index = 4)
    BigDecimal balance;

    @ApiModelProperty(value = "单号")
    @Excel(name = "单号", index = 5)
    String billNo;
    
    @ApiModelProperty(value = "相关单号")
    @Excel(name = "相关单号", index = 6)
    String linkNo;

}
