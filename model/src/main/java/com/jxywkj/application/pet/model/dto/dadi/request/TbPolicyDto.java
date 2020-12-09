package com.jxywkj.application.pet.model.dto.dadi.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className TbPolicyDto
 * @date 2020/8/10 11:52
 **/
@Data
@ApiModel(value = "请求总节点，封装投保要素信息。支持循环")
public class TbPolicyDto {

    // 保单主信息
    TbInsuranceSchema tbInsuranceSchema;

    // DIY产品所选择的条款信息
    List<TbRisk> tbRiskList;

    // 货运险信息
    TbMainCargo tbMainCargo;

    // 货运险费率
    TbFee tbFee;

    TbExpense tbExpense;
    List<TbEngage> tbEngageList;

}
