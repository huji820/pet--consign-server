package com.jxywkj.application.pet.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className xmlHead
 * @date 2020/8/6 18:16
 **/
@Data
public class XmlHead {

    @ApiModelProperty(value = "用户名")
    String sendName;
    @ApiModelProperty(value = "密码")
    String sendPwd;
    @ApiModelProperty(value = "请求序列号")
    String sendSeq;
    @ApiModelProperty(value = "请求时间")
    String sendTime;
    @ApiModelProperty(value = "系统标识")
    String sysFlag;
    @ApiModelProperty(value = "操作类型")
    String operateType;
    @ApiModelProperty(value = "接入类型标识")
    String modeFlag;
    @ApiModelProperty(value = "交易码")
    String transCode;
    @ApiModelProperty(value = "查询接口标识")
    String orderType;
    @ApiModelProperty(value = "区分电子保单接口和查询接口标识")
    String queryUrlFlag;

}
