package com.jxywkj.application.pet.model.consign;

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
 * @className OrderFlowTrend
 * @date 2020/1/7 16:11
 **/
@Data
@ApiModel(description = "订单金额去向流水")
public class OrderFlowTrend extends StationBalanceFlow {
    @ApiModelProperty(value = "站点名称")
    String stationName;

    @ApiModelProperty(value = "商家名称")
    String businessName;
}
