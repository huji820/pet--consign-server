package com.jxywkj.application.pet.model.dto.dadi.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className TbExpense
 * @date 2020/8/10 12:32
 **/
@Data
public class TbExpense {

    @ApiModelProperty(value = "是否含税")
    String disRateCalType;

    @ApiModelProperty(value = "手续费")
    String commissionRate;
}
