package com.jxywkj.application.pet.model.dto;

import com.jxywkj.application.pet.model.common.Customer;
import com.yangwang.sysframework.wechat.boot.model.WxUserInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 加密数据和用户信息
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className RawDataUserInfo
 * @date 2020/2/29 15:38
 **/
@Data
@ApiModel(description = "加密数据和用户信息")
public class RawDataUserInfo {
    @ApiModelProperty(value = "微信用户信息")
    WxUserInfo wxUserInfo;

    @ApiModelProperty(value = "加密数据")
    String encryptedData;

    @ApiModelProperty(value = "偏移量")
    String iv;

    @ApiModelProperty(value = "用户对象")
    Customer customer;

    @ApiModelProperty(value = "appType")
    String appType;
}
