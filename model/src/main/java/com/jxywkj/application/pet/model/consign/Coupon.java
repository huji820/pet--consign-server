package com.jxywkj.application.pet.model.consign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName Coupon
 * @Description 优惠券
 * @Author LiuXiangLin
 * @Date 2019/8/20 13:38
 * @Version 1.0
 **/
@Data
@ApiModel(value = "优惠券")
public class Coupon {

    @ApiModelProperty(value = "券编号")
    String couponNo;

    @ApiModelProperty(value = "券名称")
    String couponName;

    @ApiModelProperty(value = "券类型")
    String couponType;

    @ApiModelProperty(value = "券描述")
    String couponDescribe;

    @ApiModelProperty(value = "发放时间")
    String releaseTime;

    @ApiModelProperty(value = "失效时间")
    String failureTime;

    @ApiModelProperty(value = "所属人")
    String phone;

    @ApiModelProperty(value = "券状态")
    String couponState;

    @ApiModelProperty(value = "使用时间")
    String useTime;

    @ApiModelProperty(value = "使用驿站")
    String businessNo;

    public Coupon() {
    }

    public Coupon(String couponNo, String couponName, String couponType, String couponDescribe, String releaseTime, String failureTime, String phone, String couponState, String useTime, String businessNo) {
        this.couponNo = couponNo;
        this.couponName = couponName;
        this.couponType = couponType;
        this.couponDescribe = couponDescribe;
        this.releaseTime = releaseTime;
        this.failureTime = failureTime;
        this.phone = phone;
        this.couponState = couponState;
        this.useTime = useTime;
        this.businessNo = businessNo;
    }
}
