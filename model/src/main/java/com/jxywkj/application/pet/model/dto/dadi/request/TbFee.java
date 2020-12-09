package com.jxywkj.application.pet.model.dto.dadi.request;

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
 * @className TbFee
 * @date 2020/8/10 12:31
 **/
@Data
@ApiModel(value = "货运险费率")
public class TbFee {
    @ApiModelProperty(value = "币别")
    String currency;
    @ApiModelProperty(value = "支付币别")
    String currency1;
    @ApiModelProperty(value = "支付兑换率")
    String exchangeRate1;
}
