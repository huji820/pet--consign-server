package com.jxywkj.application.pet.model.consign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author GuoPengCheng
 * @Date 2020-07-21 18:36
 * @table t_consign_order
 * @Version 1.0
 */
@Data
@ApiModel(value = "线下生成工单表")
public class OfflineOrder extends Order{
    @ApiModelProperty(value = "工单标识符：1则是工单")
    String OfflineWorkOrderNo;
    @ApiModelProperty(value = "中转费")
    BigDecimal TransferFee;
    @ApiModelProperty(value = "是否代收 0:不需要 1:需要")
    String Collection;
}
