package com.jxywkj.application.pet.model.vo.area;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yang hituzi
 * @description 县/区列表
 * @date 
 */
@Data
public class AreaDistrictVO {

    @ApiModelProperty(value = "主键")
    Integer id;

    @ApiModelProperty(value = "权重")
    Integer weight;

    @ApiModelProperty(value = "编号")
    Integer cityId;

    @ApiModelProperty(value = "县/区名称")
    String name;

    @ApiModelProperty(value = "邮编编码")
    String postCode;

    @ApiModelProperty(value = "")
    String issync;
}
