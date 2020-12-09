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
 * @className tbPolicyDto
 * @date 2020/8/6 18:21
 **/
@Data
@ApiModel(value = "请求总节点")
public class TbPolicyDto {

    @ApiModelProperty(value = "")
    TbInsuranceSchema tbInsuranceSchema;
}
