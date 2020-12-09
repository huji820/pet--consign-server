package com.jxywkj.application.pet.model.consign.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className OrderInOrOutPutParam
 * @date 2020/3/17 15:26
 **/
@Data
@ApiModel(description = "出入港参数")
public class OrderInOrOutPutParam {
    @ApiModelProperty(value = "文件列表")
    String[] fileList;

    @ApiModelProperty(value = "订单编号")
    String orderNo;

    @ApiModelProperty(value = "sn")
    int sn;

    @ApiModelProperty(value = "订单类型")
    String orderType;

    @ApiModelProperty(value = "延时出港时间 单位：毫秒")
    Long delayedTime;
}
