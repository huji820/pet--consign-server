package com.jxywkj.application.pet.model.vo.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description
 * @Author Administrator
 * @Date 2019-12-08 14:03
 * @Version 1.0
 */
@Data
@ApiModel(value = "业绩对象")
public class HomePerformanceVO {

    @ApiModelProperty(value = "日期")
    String day;

    @ApiModelProperty(value = "业绩")
    BigDecimal amount;

}
