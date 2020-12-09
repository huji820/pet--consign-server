package com.jxywkj.application.pet.model.consign;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 宠物订单评价
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className ConsignOrderEvaluate
 * @date 2019/12/11 17:25
 **/
@ApiModel(description = "宠物订单评价")
@Data
public class ConsignOrderEvaluate {
    @ApiModelProperty(value = "订单")
    Order order;

    @ApiModelProperty(value = "获得的星级")
    int star;

    @ApiModelProperty(value = "评价时间")
    String dateTime;

    @ApiModelProperty(value = "评价人（电话号码）")
    String evaluator;

    @ApiModelProperty(value = "评价内容")
    String content;
}
