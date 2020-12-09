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
 * @className tbInsuranceSchema
 * @date 2020/8/10 11:48
 **/
@ApiModel(value = "保单主信息")
@Data
public class TbInsuranceSchema {

    @ApiModelProperty(value = "投保单号")
    String proposalNo;

    @ApiModelProperty(value = "预印单证号")
    String printNo;

    @ApiModelProperty(value = "请求流水号，用于做幂等校验，字符串长度不超过40位")
    String importSn;

    @ApiModelProperty(value = "填单日期")
    String inputDate;

    @ApiModelProperty(value = "投保日期")
    String insurDate;

    @ApiModelProperty(value = "被保人姓名")
    String insuredName;

    @ApiModelProperty(value = "被保人固定电话")
    String insuredPhone;

    @ApiModelProperty(value = "保险起期")
    String insurStartDate;

    @ApiModelProperty(value = "保险止期")
    String insurEndDate;

    @ApiModelProperty(value = "即时生效标识")
    String immeValidFlag;

    @ApiModelProperty(value = "即时生效起期")
    String immeValidStartDate;

    @ApiModelProperty(value = "即时生效止期")
    String immeValidEndDate;

    @ApiModelProperty(value = "主险种")
    String mainRiskCode;

    @ApiModelProperty(value = "保额，分项条款的保额，保留两位小数")
    String amount;

    @ApiModelProperty(value = "保费，保留两位小数")
    String premium;

    @ApiModelProperty(value = "费率")
    String rate;

    @ApiModelProperty(value = "系统标识")
    String sysFlag;

    @ApiModelProperty(value = "投保人姓名")
    String holderName;

    @ApiModelProperty(value = "投保人证件号码")
    String holderIdNo;

    @ApiModelProperty(value = "保人 联系人手机")
    String holderMobile;

    @ApiModelProperty(value = "投保人 联系人邮箱")
    String holderemail;

    @ApiModelProperty(value = "投保人证件类型")
    String holderidType;

    @ApiModelProperty(value = "被保人证件号")
    String insuredIdNo;

    @ApiModelProperty(value = "被保人证件类型(详见数据字段)")
    String insuredIdType;

    @ApiModelProperty(value = "被保人数量")
    String insuredNumber;

    @ApiModelProperty(value = "被保险人地址")
    String insuredAddress;

    @ApiModelProperty(value = "被保人手机")
    String insuredmobile;

    @ApiModelProperty(value = "产品代码")
    String certifyCode;

    @ApiModelProperty(value = "受益人数量")
    String custNumber;

    @ApiModelProperty(value = "份数")
    String mult;

    @ApiModelProperty(value = "保单有效标志，0：正常保单，1：作废保单")
    String polFlag;

}
