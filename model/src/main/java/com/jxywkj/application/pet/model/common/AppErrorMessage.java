package com.jxywkj.application.pet.model.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName AppErrorMessage
 * @Description 错误日志
 * @Author LiuXiangLin
 * @Date 2019/9/30 9:41
 * @Version 1.0
 **/
@Data
@ApiModel(value = "错误日志")
public class AppErrorMessage {
    @ApiModelProperty(value = "编号")
    Integer logNo;

    @ApiModelProperty(value = "手机型号")
    String phoneModel;

    @ApiModelProperty(value = "用户电话")
    String phone;

    @ApiModelProperty(value = "时间")
    String dateTime;

    @ApiModelProperty(value = "操作事件")
    String operation;

    @ApiModelProperty(value = "具体错误日志")
    String logMessage;

    @ApiModelProperty(value = "app类型")
    int appType;
}
