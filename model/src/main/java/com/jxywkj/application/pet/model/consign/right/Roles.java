package com.jxywkj.application.pet.model.consign.right;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: trunk
 * @description: 员工(角色)
 * @author: lsy
 * @create: 2020-01-15 :
 **/
@Data
@ApiModel("岗位")
public class Roles {

    @ApiModelProperty("角色主键")
    Integer roleId;

    @ApiModelProperty("角色名称")
    String roleName;

    @ApiModelProperty("角色描述")
    String roleDesc;

    @ApiModelProperty("权限列表")
    String privileges;

    @ApiModelProperty("状态  是否有效")
    Integer dataFlag;

    @ApiModelProperty("日期")
    String createDate;

    @ApiModelProperty("时间")
    String createTime;
}
