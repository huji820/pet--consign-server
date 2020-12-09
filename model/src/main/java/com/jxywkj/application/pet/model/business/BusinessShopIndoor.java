package com.jxywkj.application.pet.model.business;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: trunk
 * @description: 店铺内部图片
 * @author: lsy
 * @create: 2019-11-29 10:28
 **/
@Data
@ApiModel(description = "商家店内照片")
public class BusinessShopIndoor {
    @ApiModelProperty("主键")
    Integer     shopImgNo;
    @ApiModelProperty("商家主键")
    String      businessNo;
    @ApiModelProperty("店内环境照片")
    String      shopIndoorImg;
}
