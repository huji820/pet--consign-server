package com.jxywkj.application.pet.model.consign;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName StationWithdraw
 * @Description 站点提现表
 * @Author LiuXiangLin
 * @Date 2019/8/26 10:43
 * @Version 1.0
 **/
@Data
@ApiModel("站点提现")
@JsonIgnoreProperties(ignoreUnknown = true)
public class StationWithdraw {
    @ApiModelProperty("提现单号")
    String withdrawNo;

    @ApiModelProperty("提现时间")
    String withdrawTime;

    @ApiModelProperty("提现员工")
    Staff staff;

    @ApiModelProperty("提现金额")
    BigDecimal amount;

    @ApiModelProperty("提现状态")
    String state;

    @ApiModelProperty("所属站点")
    Station station;
}
