package com.jxywkj.application.pet.model.consign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author LiuXiangLin
 * @version 1.0
 * @className AddedWeightCage
 * @description 宠物笼具
 * @date 2019/10/19 9:36
 **/
@Data
@ApiModel(description = "宠物笼具")
public class AddedWeightCage implements Comparable<AddedWeightCage>, Serializable {
    private static final long serialVersionUID = -3956289958622565698L;
    @ApiModelProperty(value = "笼具编号")
    Integer cageNo;

    @ApiModelProperty(value = "所属站点")
    Station station;

    @ApiModelProperty(value = "笼具名称")
    String cageName;

    @ApiModelProperty(value = "运送宠物最小重量")
    BigDecimal minWeight;

    @ApiModelProperty(value = "运送宠物最大重量")
    BigDecimal maxWeight;

    @ApiModelProperty(value = "笼具重量")
    BigDecimal cageWeight;

    @ApiModelProperty(value = "笼具价格")
    BigDecimal cagePrice;

    @ApiModelProperty(value = "运输方式")
    Integer transportType;

    @ApiModelProperty(value = "是否适用于按重量计算")
    Integer useWeight;

    @ApiModelProperty(value = "是否用于按体积计算梯度")
    Integer useVolume;

    @Override
    public int compareTo(AddedWeightCage o) {
        return this.getMaxWeight().compareTo(o.getMaxWeight());
    }
}
