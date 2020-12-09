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
 * @className TbEngage
 * @date 2020/8/10 12:33
 **/
@Data
public class TbEngage {

    @ApiModelProperty(value = "序列号")
    String serialNo;
    @ApiModelProperty(value = "行序号")
    String lineNo;
    @ApiModelProperty(value = "险种")
    String riskCode;
    @ApiModelProperty(value = "条款编码")
    String clauseCode;
    @ApiModelProperty(value = "条款文字描述")
    String clauses;
    @ApiModelProperty(value = "是否为标题")
    String titleFlag;
    @ApiModelProperty(value = "标志字段")
    String flag;
}
