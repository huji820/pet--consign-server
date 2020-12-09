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
 * @className TbRisk
 * @date 2020/8/10 11:58
 **/
@Data
@ApiModel(value = "DIY产品所选择的条款信息")
public class TbRisk {

    @ApiModelProperty(value = "险种")
    String riskCode;
    @ApiModelProperty(value = "序列号，根据选择的条款数依次递增传值")
    String serialNo;
    @ApiModelProperty(value = "条款代码")
    String kindCode;
    @ApiModelProperty(value = "条款名称")
    String kindName;
    @ApiModelProperty(value = "责任代码")
    String itemCode;
    @ApiModelProperty(value = "责任名称")
    String itemName;
    @ApiModelProperty(value = "保额，分项条款的保额，保留两位小数")
    String amount;
    @ApiModelProperty(value = "基准费率，依据条款的保额与保费计算（premium/amount*1000）")
    String benchMarkRate;
    @ApiModelProperty(value = "短期费率")
    String shortTermRate;
    @ApiModelProperty(value = "每份保额，保留两位小数")
    String unitAmount;
    @ApiModelProperty(value = "保费，保留两位小数")
    String premium;
    @ApiModelProperty(value = "保额标志,是否计入总保额，Y和N")
    String amtFlag;
    @ApiModelProperty(value = "主附险标志,1-主险;2-附加险")
    String mainRiskFlag;
    @ApiModelProperty(value = "份数")
    String mult;
    @ApiModelProperty(value = "被保人数量")
    String insuredNumber;
    @ApiModelProperty(value = "分户序号、同职业类别代码传值")
    String familyNo;
    @ApiModelProperty(value = "地址代码")
    String addressNo;
}
