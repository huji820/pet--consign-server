package com.jxywkj.application.pet.model.consign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 超出宠物数量
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className BeyondPetNum
 * @date 2019/11/22 11:36
 **/
@Data
@ApiModel(description = "超出宠物数量计算")
public class BeyondPetNum {
    @ApiModelProperty(value = "所属站点")
    Station station;

    @ApiModelProperty(value = "运输类型")
    int transportType;

    @ApiModelProperty(value = "客户价")
    BigDecimal retailPrice;

    @ApiModelProperty(value = "合作价")
    BigDecimal joinPrice;
}
