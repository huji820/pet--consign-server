package com.jxywkj.application.pet.model.vo.area;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yang hituzi
 * @description 
 * @date 
 */
@Data
public class AreaProvinceVO {

    @ApiModelProperty(value = "主键")
    Integer id;

    @ApiModelProperty(value = "省份名称")
    String name;

    @ApiModelProperty(value = "编号")
    Integer orderId;

    @ApiModelProperty(value = "权重")
    Integer weight;
}
