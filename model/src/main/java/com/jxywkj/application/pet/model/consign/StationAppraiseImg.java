package com.jxywkj.application.pet.model.consign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 站点评价图片model
 */
@Data
@ApiModel("站点服务评价图片实体类")
public class StationAppraiseImg {

    @ApiModelProperty("站点评价图片主键")
    int appraiseImgNo;

    @ApiModelProperty("站点服务评价")
    StationAppraise stationAppraise;

    @ApiModelProperty("评价图片")
    String appraiseImg;
}
