package com.jxywkj.application.pet.model.consign.vo;

import com.jxywkj.application.pet.model.business.BusinessBalanceFlow;
import com.jxywkj.application.pet.model.consign.ConsignInsureFlow;
import com.jxywkj.application.pet.model.consign.StationBalanceFlow;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 商家流水Vo
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className BusinessBalanceFlowVo
 * @date 2020/4/23 9:48
 **/
@Data
@ApiModel(value = "商家流水VO")
public class BusinessBalanceFlowVo extends BusinessBalanceFlow {
    @ApiModelProperty(value = "站点余额流水")
    List<StationBalanceFlow> stationBalanceFlowList;

    @ApiModelProperty(value = "商家余额流水")
    List<BusinessBalanceFlow> businessBalanceFlowList;

    @ApiModelProperty(value = "投保流水")
    ConsignInsureFlow consignInsureFlow;
}
