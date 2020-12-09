package com.jxywkj.application.pet.model.consign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 订单金额走向
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className OrderAmountTrend
 * @date 2020/1/6 15:01
 **/
@Data
@ApiModel(description = "订单金额走向")
public class OrderAmountTrend extends Order {
    @ApiModelProperty(value = "差价汇总")
    BigDecimal premiumTotal;
}
