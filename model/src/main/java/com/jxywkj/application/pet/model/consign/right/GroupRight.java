package com.jxywkj.application.pet.model.consign.right;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: trunk
 * @description: 权限组表(岗位)
 * @author: lsy
 * @create: 2020-01-15 :
 **/
@Data
@ApiModel("岗位")
public class GroupRight {

    @ApiModelProperty("岗位id")
    Integer tgrId;

    @ApiModelProperty("权限id")
    Integer trId;

    @ApiModelProperty("权限类型")
    Integer rightType;

    @ApiModelProperty("权限组名称(岗位名称)")
    String tgrName;

    @ApiModelProperty("权限")
    String trIds;
}
