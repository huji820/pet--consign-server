package com.jxywkj.application.pet.model.consign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName OrderState
 * @Description 订单当前状态
 * @Author LiuXiangLin
 * @Date 2019/7/22 14:29
 * @Version 1.0
 **/
@Data
@ApiModel("当前订单状态")
public class OrderState implements Comparable<OrderState> {
    @ApiModelProperty("订单单号")
    String orderNo;

    @ApiModelProperty("订单SN")
    int sn;

    @ApiModelProperty("日期")
    String date;

    @ApiModelProperty("时间")
    String time;

    @ApiModelProperty("当前描述")
    String currentPosition;

    @ApiModelProperty(value = "当前状态")
    String orderType;

    @ApiModelProperty("所属站点")
    Station station;

    @ApiModelProperty("图片或者视频")
    List<OrderMedia> orderMediaList;

    @ApiModelProperty("图片集合")
    List<OrderMedia> pictureList;

    @ApiModelProperty("视频集合")
    List<OrderMedia> videoList;

    public OrderState() {
    }

    public OrderState(String orderNo, int sn, String date, String time, String currentPosition, String orderType, Station station) {
        this.orderNo = orderNo;
        this.sn = sn;
        this.date = date;
        this.time = time;
        this.currentPosition = currentPosition;
        this.orderType = orderType;
        this.station = station;
    }

    @Override
    public int compareTo(OrderState o) {
        return sn - o.getSn();
    }
}
