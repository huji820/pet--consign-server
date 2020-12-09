package com.jxywkj.application.pet.model.consign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javafx.geometry.Pos;
import lombok.Data;

/**
 * @ClassName BusinessPosition
 * @Description 站点接送位置
 * @Author LiuXiangLin
 * @Date 2019/7/24 14:33
 * @Version 1.0
 **/
@Data
@ApiModel("站点接送位置")
public class Position {
    @ApiModelProperty(value = "机场编号")
    Integer positionNo;

    @ApiModelProperty(value = "所属站点")
    Station station;

    @ApiModelProperty(value = "省")
    String province;

    @ApiModelProperty(value = "市")
    String city;

    @ApiModelProperty(value = "区")
    String region;

    @ApiModelProperty(value = "地址")
    String detailAddress;

    @ApiModelProperty(value = "经度")
    Double longitude;

    @ApiModelProperty(value = "纬度")
    Double latitude;

    @ApiModelProperty(value = "类型")
    Integer type;

    /**
     * <p>
     * 复制一个对象
     * </p>
     *
     * @param position 源对象
     * @return com.jxywkj.application.pet.model.consign.Position
     * @author LiuXiangLin
     * @date 10:36 2020/4/16
     **/
    public Position copy(Position position) {
        Position result = new Position();
        result.setPositionNo(position.getPositionNo());
        result.setStation(position.getStation());
        result.setProvince(position.getProvince());
        result.setCity(position.getCity());
        result.setRegion(position.getRegion());
        result.setDetailAddress(position.getDetailAddress());
        result.setLongitude(position.getLongitude());
        result.setLatitude(position.getLatitude());
        result.setType(position.getType());

        return result;
    }
}
