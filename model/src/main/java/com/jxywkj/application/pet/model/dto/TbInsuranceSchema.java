package com.jxywkj.application.pet.model.dto;

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
 * @className TbInsuranceSchema
 * @date 2020/8/6 18:21
 **/
@Data
@ApiModel(value = "保单主信息")
public class TbInsuranceSchema {

    @ApiModelProperty(value = "投保人姓名")
    String holderName;
    @ApiModelProperty(value = "投保人证件号")
    String holderIdNo;
    @ApiModelProperty(value = "保单号")
    String policyNo;
}
