package com.jxywkj.application.pet.model.consign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author LiuXiangLin
 * @version 1.0
 * @className AddedVolumeCage
 * @description 按体积算宠物笼具
 * @date 2019/10/19 9:34
 **/
@Data
@ApiModel(description = "按体积算笼具")
public class AddedVolumeCage implements Comparable<AddedVolumeCage> {
    @ApiModelProperty(value = "关联笼具")
    AddedWeightCage addedWeightCage;

    @ApiModelProperty(value = "所属运输路线")
    Transport transport;

    @ApiModelProperty(value = "起步价(客户)")
    BigDecimal startingRetailPrice;

    @ApiModelProperty(value = "起步价(合作商)")
    BigDecimal startingJoinPrice;

    public int compareTo(AddedVolumeCage o) {
        return this.getAddedWeightCage().compareTo(o.getAddedWeightCage());
    }
}
