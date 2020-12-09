package com.jxywkj.application.pet.model.consign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName AddedInsure
 * @Description 增值服务_保价
 * @Author LiuXiangLin
 * @Date 2019/7/25 13:58
 * @Version 1.0
 **/
@Data
@ApiModel("增值服务_保价")
public class AddedInsure {
    @ApiModelProperty("编号")
    int insureNo;

    @ApiModelProperty("保价费率")
    BigDecimal rate;

    @ApiModelProperty("所属站点")
    Station station;

    @ApiModelProperty("保价金额")
    BigDecimal insureAmount;

    public AddedInsure() {
    }

    public AddedInsure(int insureNo, BigDecimal rate, Station station, BigDecimal insureAmount) {
        this.insureNo = insureNo;
        this.rate = rate;
        this.station = station;
        this.insureAmount = insureAmount;
    }

    public AddedInsure(BigDecimal rate) {
        this.rate = rate;
    }
}
