package com.jxywkj.application.pet.model.consign.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName OnDoorServiceDTO
 * @Author LiuXiangLin
 * @Date 2019/7/26 10:13
 * @Version 1.0
 **/
@Data
@ApiModel("上门接宠数据传输对象")
public class OnDoorServiceDTO {
    @ApiModelProperty("上门地址")
    String address;

    @ApiModelProperty("实际距离")
    BigDecimal distance;

    @ApiModelProperty("需要花费金额")
    BigDecimal spendAmount;
}
