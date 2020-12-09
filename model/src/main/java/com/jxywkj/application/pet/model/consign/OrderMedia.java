package com.jxywkj.application.pet.model.consign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName OrderMedia
 * @Description 订单媒体对象
 * @Author LiuXiangLin
 * @Date 2019/7/22 14:25
 * @Version 1.0
 **/
@Data
@ApiModel("订单媒体对象")
public class OrderMedia {
    @ApiModelProperty
    String mediaNo;

    @ApiModelProperty("订单单号")
    String orderNo;

    @ApiModelProperty("订单SN")
    int sn;

    @ApiModelProperty("图片或者视频地址")
    String mediaAddress;

    @ApiModelProperty("日期")
    String date;

    @ApiModelProperty("时间")
    String time;

    @ApiModelProperty("媒体类型 视频、图片或者音频")
    String mediaType;

    @ApiModelProperty("媒体名称")
    String mediaName;

    @ApiModelProperty("显示路径")
    String viewAddress;

    public OrderMedia() {
    }

    public OrderMedia(String mediaNo, String orderNo, int sn, String mediaAddress, String date, String time, String mediaType, String mediaName) {
        this.mediaNo = mediaNo;
        this.orderNo = orderNo;
        this.sn = sn;
        this.mediaAddress = mediaAddress;
        this.date = date;
        this.time = time;
        this.mediaType = mediaType;
        this.mediaName = mediaName;
    }
}
