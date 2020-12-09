package com.jxywkj.application.pet.model.consign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 所用笼具
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className AddedPetCage
 * @date 2019/10/21 18:15
 **/
@Data
@ApiModel(description = "所用笼具")
public class AddedPetCage {
    @ApiModelProperty(value = "匹配笼具")
    AddedVolumeCage addedVolumeCage;

    @ApiModelProperty(value = "花费金额")
    BigDecimal amount;

    @ApiModelProperty(value = "差价")
    BigDecimal differencePrice;
}
