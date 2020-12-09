package com.jxywkj.application.pet.model.common;

import com.jxywkj.application.pet.common.annotation.Excel;
import com.jxywkj.application.pet.common.enums.TransportTypeEnum;
import com.jxywkj.application.pet.common.utils.StringUtils;
import lombok.Data;

import java.util.regex.Pattern;

/**
 * @ClassName ExcelTransportDO
 * @Description 运输路线实体类
 * @Author LiuXiangLin
 * @Date 2019/7/23 9:28
 * @Version 1.0
 **/


@Data
public class ExcelTransportDO {
    @Excel(name = "路线编号", index = 1)
    String transportNo;

    @Excel(name = "路线名称", index = 2)
    String transportName;

    @Excel(name = "开始城市", index = 3)
    String startCity;

    @Excel(name = "目标城市", index = 4)
    String endCity;

    @Excel(name = "运输类型", index = 5)
    String transportType;

    @Excel(name = "起步重量", index = 6)
    String startingWeight;

    @Excel(name = "起步价（客户）", index = 7)
    String startingRetailPrice;

    @Excel(name = "起步价（商户）", index = 8)
    String startingJoinPrice;

    @Excel(name = "续重费（客户）", index = 9)
    String continueRetailPrice;

    @Excel(name = "续重费（商户）", index = 10)
    String continueJoinPrice;

    @Excel(name = "站点编号", index = 11)
    Integer stationNo;

    @Excel(name = "合作伙伴编号", index = 12)
    Integer partnerNo;


    public void setTransportType(String transportType) {
        String regex = "[0-9]*";
        if (!StringUtils.isBlank(transportType) &&
                Pattern.matches(regex, transportType)) {
            TransportTypeEnum transportTypeEnum = TransportTypeEnum.getTransportTypeEnum(Integer.valueOf(transportType));
            this.transportType = transportTypeEnum == null ? "" : transportTypeEnum.getName();
        } else {
            this.transportType = "";
        }
    }
}
