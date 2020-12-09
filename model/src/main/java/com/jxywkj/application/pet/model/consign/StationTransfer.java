package com.jxywkj.application.pet.model.consign;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "中转站点")
public class StationTransfer {
    String orderNo;
    String stationNo;
    int sn;
}
