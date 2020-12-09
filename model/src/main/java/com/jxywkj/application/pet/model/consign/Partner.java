package com.jxywkj.application.pet.model.consign;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2019-06-23 18:41
 * @Version 1.0
 */
@Data
@ApiModel(description = "托运站的合作伙伴")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Partner {

    @ApiModelProperty(value = "合作伙伴编号")
    int partnerNo;

    @ApiModelProperty(value = "合作伙伴名称")
    String partnerName;

    @ApiModelProperty(value = "精度")
    double lat;

    @ApiModelProperty(value = "纬度")
    double lng;

    @ApiModelProperty(value = "联系人")
    String contact;

    @ApiModelProperty(value = "联系号码")
    long phone;

    @ApiModelProperty(value = "省")
    String province;

    @ApiModelProperty(value = "市")
    String city;

    @ApiModelProperty("县")
    String county;

    @ApiModelProperty(value = "所属托运站")
    Station station;

    @ApiModelProperty(value = "联系人")
    List<PartnerContact> partnerContacts;

    public Partner() {
    }

    public Partner(int partnerNo) {
        this.partnerNo = partnerNo;
    }

    public Partner(String partnerName, double lat, double lng, boolean worked, Station station) {
        this.partnerName = partnerName;
        this.lat = lat;
        this.lng = lng;
        this.station = station;
    }
}
