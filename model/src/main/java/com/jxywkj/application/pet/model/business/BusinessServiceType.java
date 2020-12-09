package com.jxywkj.application.pet.model.business;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: trunk
 * @description: 商家服务范围
 * @author: lsy
 * @create: 2019-11-29 :
 **/

@Data
@ApiModel(description = "商家服务范围")
public class BusinessServiceType {
    @ApiModelProperty
    Integer serviceNo;

    @ApiModelProperty
    String serviceContent;

    @ApiModelProperty
    Integer serviceState;
}
