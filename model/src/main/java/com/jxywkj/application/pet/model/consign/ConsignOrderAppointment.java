package com.jxywkj.application.pet.model.consign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 运宠订单司机预约
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className ConsignOrderAppointment
 * @date 2019/12/16 9:02
 **/
@Data
@ApiModel(description = "运宠订单司机预约")
public class ConsignOrderAppointment {
    @ApiModelProperty(value = "订单")
    Order order;



}
