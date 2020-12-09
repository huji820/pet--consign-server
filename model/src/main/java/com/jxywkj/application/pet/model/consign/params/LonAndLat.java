package com.jxywkj.application.pet.model.consign.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName LonAndLat
 * @Author LiuXiangLin
 * @Date 2019/7/26 10:36
 * @Version 1.0
 **/
@ApiModel("经纬度对象")
@Data
public class LonAndLat {
    @ApiModelProperty(value = "经度")
    String longitude;

    @ApiModelProperty(value = "纬度")
    String latitude;

    public LonAndLat(String longitude, String latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getStringLonAndLat() {
        return this.longitude + "," + this.latitude;
    }

    public LonAndLat() {
    }
}
