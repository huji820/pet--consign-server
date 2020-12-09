package com.jxywkj.application.pet.model.consign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName OrderField
 * @Description
 * @Author LiuXiangLin
 * @Date 2019/9/25 9:20
 * @Version 1.0
 **/
@Data
@ApiModel(description = "订单列")
public class OrderField {
    @ApiModelProperty(value = "字段编号")
    int fieldNo;

    @ApiModelProperty(value = "字段")
    String field;

    @ApiModelProperty(value = "字段名称")
    String fieldName;

}
