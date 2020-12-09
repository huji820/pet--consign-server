package com.jxywkj.application.pet.model.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description
 * @Author Administrator
 * @Date 2019-12-06 14:31
 * @Version 1.0
 */
@ApiModel(value = "管理员")
@Data
public class Manager {

    @ApiModelProperty(value = "管理员编号")
    String managerNo;

    @ApiModelProperty(value = "管理员名称")
    String managerName;

    @ApiModelProperty(value = "手机号码")
    String phone;

    @ApiModelProperty(value = "是否活跃")
    boolean active;
}
