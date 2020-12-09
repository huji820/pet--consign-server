package com.jxywkj.application.pet.model.common;

import com.yangwang.sysframework.wechat.boot.model.WxUserInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 登录信息
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className LoginInfo
 * @date 2019/11/29 16:23
 **/
@Data
@ApiModel(description = "登录信息")
public class LoginInfo {
    @ApiModelProperty(value = "微信登录code")
    String code;

    @ApiModelProperty(value = "加密数据")
    String encryptedData;

    @ApiModelProperty(value = "偏移量")
    String iv;

    @ApiModelProperty(value = "微信用户信息")
    WxUserInfo wxUserInfo;

    @ApiModelProperty(value = "分享人openid")
    String shareOpenId;

    @ApiModelProperty(value = "分享人unionId")
    String shareUnionId;
}
