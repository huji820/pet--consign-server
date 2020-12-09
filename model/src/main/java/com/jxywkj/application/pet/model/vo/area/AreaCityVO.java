package com.jxywkj.application.pet.model.vo.area;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yang hituzi
 * @description 地级市列表
 * @date 2020年4月23日 13:36:44
 */
@Data
public class AreaCityVO {

    @ApiModelProperty(value = "主键 ")
    Integer id;

    @ApiModelProperty(value = "权重")
    Integer weight;

    @ApiModelProperty(value = "地级市名称")
    String name;

    @ApiModelProperty(value = "编号")
    Integer provinceId;

    @ApiModelProperty(value = "")
    String issync;
}
