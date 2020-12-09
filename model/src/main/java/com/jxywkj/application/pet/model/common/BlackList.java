package com.jxywkj.application.pet.model.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 黑名单
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className BlackList
 * @date 2020/1/8 9:03
 **/
@Data
@ApiModel(description = "黑名单")
public class BlackList {
    @ApiModelProperty(value = "主键")
    int id;

    @ApiModelProperty(value = "电话号码")
    String phone;

    @ApiModelProperty(value = "添加时间")
    String dateTime;

    public BlackList() {
    }

    public BlackList(String phone, String dateTime) {
        this.phone = phone;
        this.dateTime = dateTime;
    }
}