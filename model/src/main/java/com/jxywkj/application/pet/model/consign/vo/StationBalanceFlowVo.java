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
 * 站点流水Vo
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className StationBalanceFlowVo
 * @date 2020/4/23 9:24
 **/
@Data
@ApiModel(description = "站点流水Vo")
public class StationBalanceFlowVo extends StationBalanceFlow {
    @ApiModelProperty(value = "站点流水")
    List<StationBalanceFlow> stationBalanceFlowList;

    @ApiModelProperty(value = "商家流水")
    List<BusinessBalanceFlow> businessBalanceFlowList;

    @ApiModelProperty(value = "投保流水")
    ConsignInsureFlow consignInsureFlow;
}
