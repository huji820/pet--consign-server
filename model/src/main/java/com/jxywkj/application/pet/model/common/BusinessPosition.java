package com.jxywkj.application.pet.model.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName BusinessPosition
 * @Author LiuXiangLin
 * @Date 2019/8/28 17:41
 * @Version 1.0
 **/
@ApiModel
@Data
public class BusinessPosition {
    @ApiModelProperty(value = "原始经度")
    double longitude;

    @ApiModelProperty(value = "原始纬度")
    double latitude;

    @ApiModelProperty("开始经度")
    double startLongitude;

    @ApiModelProperty("结束经度")
    double endLongitude;

    @ApiModelProperty("开始纬度")
    double startLatitude;

    @ApiModelProperty("结束纬度")
    double endLatitude;

    public BusinessPosition(double longitude, double latitude, double startLongitude, double endLongitude, double startLatitude, double endLatitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.startLongitude = startLongitude;
        this.endLongitude = endLongitude;
        this.startLatitude = startLatitude;
        this.endLatitude = endLatitude;
    }

    public BusinessPosition() {
    }
}
