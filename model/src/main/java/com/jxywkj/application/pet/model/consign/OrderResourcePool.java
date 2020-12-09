package com.jxywkj.application.pet.model.consign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName OrderResourcePool
 * @Description 资源池对象
 * @Version 1.0
 **/
@Data
@ApiModel("资源池对象")
public class OrderResourcePool {
    @ApiModelProperty("自增主键")
    Integer resourcePoolNo;

    @ApiModelProperty("订单单号")
    String orderNo;

    @ApiModelProperty("节点名称")
    String node;

    @ApiModelProperty("延时时间")
    Long delayTime;

    @ApiModelProperty("备注")
    String remarks;

    @ApiModelProperty("资源池数据所属站点")
    String stationNo;

    public OrderResourcePool(String orderNo,String node,Long delayTime,String remarks,String stationNo){
        this.orderNo = orderNo;
        this.node = node;
        this.delayTime = delayTime;
        this.remarks = remarks;
        this.stationNo = stationNo;
    }

    public OrderResourcePool() {
    }
}
