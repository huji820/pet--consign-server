package com.jxywkj.application.pet.model.dto.dadi;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className XmlHead
 * @date 2020/8/10 11:48
 **/
@Data
public class XmlHead {
    @ApiModelProperty(value = "操作类型")
    String operateType;
    @ApiModelProperty(value = "系统标识")
    String sysFlag;
    @ApiModelProperty(value = "交易码")
    String transCode;
    @ApiModelProperty(value = "总保额")
    String sumAmount;
    @ApiModelProperty(value = "总保费")
    String sumPremium;
    @ApiModelProperty(value = "银联支付总保额")
    String payPremium;
    @ApiModelProperty(value = "费率")
    String rate;
    @ApiModelProperty(value = "接入类型标识")
    String modeFlag;
}
