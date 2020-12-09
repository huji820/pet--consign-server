package com.jxywkj.application.pet.model.vo;

import com.jxywkj.application.pet.model.business.Business;
import com.jxywkj.application.pet.model.business.BusinessServiceType;
import com.jxywkj.application.pet.model.business.BusinessShopIndoor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @program: trunk
 * @description: Business扩展
 * @author: lsy
 * @create: 2019-11-29 10:26
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class BusinessVo extends Business {
    @ApiModelProperty("店内图片至少3张，最多9张")
    List<BusinessShopIndoor> businessShopIndoors;

    @ApiModelProperty("商家服务范围")
    List<BusinessServiceType> businessServiceTypes;
}
