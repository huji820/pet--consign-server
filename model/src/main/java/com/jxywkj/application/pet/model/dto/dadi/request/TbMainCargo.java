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
 * @className TbMainCargo
 * @date 2020/8/10 12:03
 **/
@Data
@ApiModel(value = "货运险信息")
public class TbMainCargo {
    @ApiModelProperty(value = "订单号")
    String carrybillno;
    @ApiModelProperty(value = "发票号")
    String invoiceNo;
    @ApiModelProperty(value = "起始地名称")
    String startSiteName;
    @ApiModelProperty(value = "终止地名称")
    String endSiteName;
    @ApiModelProperty(value = "运输方式")
    String conveyance;
    @ApiModelProperty(value = "航次")
    String voyageNo;
    @ApiModelProperty(value = "提单号")
    String ladingNo;

    @ApiModelProperty(value = "包装及数量")
    String quantity;
    @ApiModelProperty(value = "保险项目描述")
    String itemDescription;
    @ApiModelProperty(value = "中转地名称")
    String viaSiteName;
    @ApiModelProperty(value = "中转地代码")
    String viasiteCode;
    @ApiModelProperty(value = "加成比例")
    String plusRate;
    @ApiModelProperty(value = "运具名称")
    String blname;
    @ApiModelProperty(value = "发动机号")
    String blNo;
    @ApiModelProperty(value = "发票金额币别")
    String invoiceCurrency;
    @ApiModelProperty(value = "标的唛头")
    String marks;


}
