package com.jxywkj.application.pet.model.consign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName partnerContact
 * @Description: 站点联系人
 * @Author Aze
 * @Date 2019/7/8 11:04
 * @Version 1.0
 **/
@Data
@ApiModel(description = "站点联系人")
public class PartnerContact {

    @ApiModelProperty(value = "联系人编号")
    int partnerNo;

    @ApiModelProperty(value = "联系人名称")
    String contact ;

    @ApiModelProperty(value = "联系人电话")
    long phone;
}
