package com.jxywkj.application.pet.model.consign.right;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: trunk
 * @description: 权限表
 * @author: lsy
 * @create: 2020-01-15 :
 **/
@Data
@ApiModel("增值服务_保价")
public class Right {
    @ApiModelProperty("权限主键")
    Integer trId;

    @ApiModelProperty("父权限")
    String parentTrId;

    @ApiModelProperty("权限名称")
    String rightName;

    @ApiModelProperty("权限描述")
    String description;


}
