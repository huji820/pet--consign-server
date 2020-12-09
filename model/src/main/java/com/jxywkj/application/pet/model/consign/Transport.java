package com.jxywkj.application.pet.model.consign;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author hj
 * @Date 2019-06-18 22:43
 * @Version 1.0
 */
@ApiModel(value = "运输线路")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transport {

    @ApiModelProperty(value = "线路主键")
    int transportNo;

    @ApiModelProperty(value = "开始")
    String startCity;

    @ApiModelProperty(value = "开始城市区")
    String startRegion;

    @ApiModelProperty(value = "终点城市")
    String endCity;

    @ApiModelProperty(value = "终点城市区")
    String endRegion;

    @ApiModelProperty(value = "运输线路名称")
    String transportName;

    @ApiModelProperty(value = "运输方式")
    String transportType;

    @ApiModelProperty(value = "起步重量")
    BigDecimal startingWeight;

    @ApiModelProperty(value = "起步价(客户)")
    BigDecimal startingRetailPrice;

    @ApiModelProperty(value = "起步价(合作商)")
    BigDecimal startingJoinPrice;

    @ApiModelProperty(value = "续重费(客户)")
    BigDecimal continueRetailPrice;

    @ApiModelProperty(value = "续重费(合作商)")
    BigDecimal continueJoinPrice;

    @ApiModelProperty(value = "所属城市外键")
    Integer cityNo;

    @ApiModelProperty(value = "所属的合作伙伴")
    Partner partner;

    @ApiModelProperty(value = "线路最大承重重量")
    BigDecimal maxWeight;

    public Transport() {
    }

    public Transport(String startCity, String endCity, String transportType) {
        this.startCity = startCity;
        this.endCity = endCity;
        this.transportType = transportType;
    }

    public Transport(int transportNo, String startCity, String startRegion, String endCity, String endRegion, String transportName, String transportType, BigDecimal startingWeight, BigDecimal startingRetailPrice, BigDecimal startingJoinPrice, BigDecimal continueRetailPrice, BigDecimal continueJoinPrice, Integer cityNo, Partner partner) {
        this.transportNo = transportNo;
        this.startCity = startCity;
        this.startRegion = startRegion;
        this.endCity = endCity;
        this.endRegion = endRegion;
        this.transportName = transportName;
        this.transportType = transportType;
        this.startingWeight = startingWeight;
        this.startingRetailPrice = startingRetailPrice;
        this.startingJoinPrice = startingJoinPrice;
        this.continueRetailPrice = continueRetailPrice;
        this.continueJoinPrice = continueJoinPrice;
        this.cityNo = cityNo;
        this.partner = partner;
    }

    public static Transport defaultTransport(String startCity ,String endCity, String transportType){
        Transport transport = new Transport();
        transport.setTransportNo(-1);
        transport.setStartCity(startCity);
        transport.setStartRegion("");
        transport.setEndCity(endCity);
        transport.setEndRegion("");
        transport.setTransportName(startCity+"-"+endCity);
        transport.setTransportType(transportType);
        transport.setCityNo(-1);
        return transport;
    }
}
