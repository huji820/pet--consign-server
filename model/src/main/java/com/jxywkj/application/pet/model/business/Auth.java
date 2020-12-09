package com.jxywkj.application.pet.model.business;

import com.yangwang.sysframework.utils.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * <p>
 * 商家认证
 * </p>
 *
 * @author LiuXiangLin
 * @date 15:32 2020/3/14
 **/
@Data
@ApiModel(value = "商家认证表")
public class Auth {
    @ApiModelProperty(value = "商家")
    Business business;

    @ApiModelProperty(value = "认证类型")
    Integer businessAuthType;

    @ApiModelProperty(value = "基础资料")
    String baseData;

    @ApiModelProperty(value = "服务内容")
    String services;

    @ApiModelProperty(value = "创建日期")
    String createDate;

    @ApiModelProperty(value = "创建时间")
    String createTime;

    @ApiModelProperty(value = "真实姓名")
    String realName;

    @ApiModelProperty(value = "身份证号")
    String idCard;

    @ApiModelProperty(value = "法人手持身份证照片")
    String legalImg;

    @ApiModelProperty(value = "营业执照编号")
    String licenseNo;

    @ApiModelProperty(value = "营业执照照片")
    String licenseImg;

    @ApiModelProperty(value = "店铺名称")
    String shopName;

    @ApiModelProperty(value = "省")
    String province;

    @ApiModelProperty(value = "市")
    String city;

    @ApiModelProperty(value = "区")
    String area;

    @ApiModelProperty(value = "详细的地址")
    String detailAddress;

    @ApiModelProperty(value = "门头照片")
    String[] shopImg;

    String shopImages;

    /**
     * <p>
     * 设置默认的时间与日期
     * </p>
     *
     * @return void
     * @author LiuXiangLin
     * @date 17:27 2020/3/19
     **/
    public Auth setDefaultDateTime() {
        this.createDate = DateUtil.format(new Date(), DateUtil.FORMAT_SIMPLE);
        this.createTime = DateUtil.format(new Date(), DateUtil.FORMAT_TIME);
        return this;
    }

}
