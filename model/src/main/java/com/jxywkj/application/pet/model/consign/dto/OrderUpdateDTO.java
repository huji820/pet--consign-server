package com.jxywkj.application.pet.model.consign.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LiuXiangLin
 * @version 1.0
 * @className OrderUpdateDTO
 * @description 订单更新
 * @date 2019/10/10 16:14
 **/
@Data
@ApiModel(description = "更新订单")
public class OrderUpdateDTO {
    @ApiModelProperty(value = "订单单号")
    String orderNo;

    @ApiModelProperty(value = "收件人姓名")
    String receiverName;

    @ApiModelProperty(value = "收件人电话")
    String receiverPhone;

    @ApiModelProperty(value = "寄件人姓名")
    String senderName;

    @ApiModelProperty(value = "寄件人联系电话")
    String senderPhone;
}
