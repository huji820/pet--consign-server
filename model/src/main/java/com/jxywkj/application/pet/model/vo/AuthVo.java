package com.jxywkj.application.pet.model.vo;

import com.jxywkj.application.pet.model.business.Auth;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 认证VO
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className AuthVo
 * @date 2020/5/20 9:21
 **/
@Data
@ApiModel(description = "认证VO")
public class AuthVo extends Auth {
    @ApiModelProperty(value = "起始营业时间")
    String startHours;

    @ApiModelProperty(value = "结束营业时间")
    String endHours;

    @ApiModelProperty(value = "描述")
    String describe;

    @ApiModelProperty(value = "联系人")
    String contact;

    @ApiModelProperty(value = "商家微信图片")
    String weChatImg;
}
