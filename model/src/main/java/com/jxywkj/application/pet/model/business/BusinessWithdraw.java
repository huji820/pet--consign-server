package com.jxywkj.application.pet.model.business;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 商家提现
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className BusinessWithdraw
 * @date 2019/12/5 15:18
 **/
@Data
@ApiModel(description = "商家提现")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusinessWithdraw {
    @ApiModelProperty(value = "提现编号")
    String withdrawNo;

    @ApiModelProperty(value = "提现时间")
    String withdrawTime;

    @ApiModelProperty(value = "提现驿站")
    Business business;

    @ApiModelProperty(value = "提现金额")
    BigDecimal amount;

    @ApiModelProperty(value = "提现状态")
    String state;
}
