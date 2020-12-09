package com.jxywkj.application.pet.model.consign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName OrderResourcePool
 * @Description 资源对象
 * @Version 1.0
 **/
@Data
@ApiModel("资源对象")
public class OrderResource {
    @ApiModelProperty
    String resourceNo;

    @ApiModelProperty("资源地址")
    String resourceAddress;

    @ApiModelProperty("资源池")
    OrderResourcePool resourcePool;

    @ApiModelProperty("日期")
    String date;

    @ApiModelProperty("时间")
    String time;

    @ApiModelProperty("资源类型")
    String resourceType;

    @ApiModelProperty("资源名称")
    String resourceName;

    public OrderResource(String resourceAddress, OrderResourcePool resourcePool,
                         String date, String time, String resourceType, String resourceName) {
        this.resourceAddress = resourceAddress;
        this.resourcePool = resourcePool;
        this.date = date;
        this.time = time;
        this.resourceType = resourceType;
        this.resourceName = resourceName;
    }
    public OrderResource(){ }
}
