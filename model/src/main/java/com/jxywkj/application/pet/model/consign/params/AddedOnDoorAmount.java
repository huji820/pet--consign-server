package com.jxywkj.application.pet.model.consign.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 上门金额
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className AddedOnDoorAmount
 * @date 2020/4/16 11:45
 **/
@Data
@ApiModel(description = "上门接送宠金额")
public class AddedOnDoorAmount {
    @ApiModelProperty(value = "金额")
    BigDecimal amount;

    @ApiModelProperty(value = "经度")
    Double lnt;

    @ApiModelProperty(value = "纬度")
    Double lat;

    @ApiModelProperty(value = "距离")
    BigDecimal distance;
}
