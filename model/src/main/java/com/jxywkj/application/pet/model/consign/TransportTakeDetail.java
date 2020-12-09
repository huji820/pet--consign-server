package com.jxywkj.application.pet.model.consign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * /**
 * <p>
 * 运输路线提货详情
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className TransportTakeDetail
 * @date 2020/4/21 9:03
 **/
@Data
@ApiModel(description = "运输路线提货详情")
public class TransportTakeDetail {
    @ApiModelProperty(value = "订单编号")
    Long transportTakeDetailNo;

    @ApiModelProperty(value = "所属运输路线")
    Transport transport;

    @ApiModelProperty(value = "联系人")
    String contact;

    @ApiModelProperty(value = "电话号码")
    String phone;

    @ApiModelProperty(value = "省")
    String province;

    @ApiModelProperty(value = "市")
    String city;

    @ApiModelProperty(value = "区")
    String region;

    @ApiModelProperty(value = "详细地址")
    String detailAddress;

    @ApiModelProperty(value = "经度")
    Double longitude;

    @ApiModelProperty(value = "纬度")
    Double latitude;

    @ApiModelProperty(value = "航空二字码")
    String code;
}
