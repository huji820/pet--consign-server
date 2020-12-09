package com.jxywkj.application.pet.model.consign;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yangwang.sysframework.utils.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 托运的托运站
 *
 * @Author huji
 * @Date 2019-06-18 21:55
 * @Version 1.0
 */
@Data
@ApiModel(value = "托运站")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Station {

    @ApiModelProperty(value = "托运站编号")
    int stationNo;

    @ApiModelProperty(value = "托运站名称")
    String stationName;

    @ApiModelProperty(value = "客服电话")
    String servicePhone;

    @ApiModelProperty(value = "省")
    String province;

    @ApiModelProperty(value = "市")
    String city;

    @ApiModelProperty(value = "区")
    String district;

    @ApiModelProperty(value = "经度")
    double lng;

    @ApiModelProperty(value = "纬度")
    double lat;

    @ApiModelProperty(value = "联系人")
    String contact;

    @ApiModelProperty(value = "联系电话")
    String phone;

    @ApiModelProperty(value = "联系地址")
    String address;

    @ApiModelProperty(value = "微信的OPENID")
    String openId;

    @ApiModelProperty(value = "营业执照")
    String stationLicenseImage;

    @ApiModelProperty(value = "身份证正面")
    String idcardFrontImage;

    @ApiModelProperty(value = "身份证正面")
    String idcardBackImage;

    @ApiModelProperty(value = "入驻日期")
    String intoDate;

    @ApiModelProperty(value = "运输路线")
    List<Transport> transports;

    @ApiModelProperty(value = "合作伙伴")
    List<Partner> partners;

    @ApiModelProperty(value = "状态:-1、淘汰;1、待审核;2、正常")
    short state;

    @ApiModelProperty(value = "运输方式")
    int transportType;

    @ApiModelProperty(value = "机场位置")
    Position position;

    @ApiModelProperty(value = "收款二维码")
    String collectionQRCode;

    public Station() {
    }

    public Station(int stationNo) {
        this.stationNo = stationNo;
    }

    public Station(int stationNo, String stationName, String province,
                   String city, String district, double lng, double lat,
                   String contact, String phone, String address,
                   String openId, String stationLicenseImage,
                   String idcardFrontImage, String idcardBackImage,
                   String intoDate, List<Transport> transports,
                   List<Partner> partners, short state, int transportType,
                   Position position,String collectionQRCode) {
        this.stationNo = stationNo;
        this.stationName = stationName;
        this.province = province;
        this.city = city;
        this.district = district;
        this.lng = lng;
        this.lat = lat;
        this.contact = contact;
        this.phone = phone;
        this.address = address;
        this.openId = openId;
        this.stationLicenseImage = stationLicenseImage;
        this.idcardFrontImage = idcardFrontImage;
        this.idcardBackImage = idcardBackImage;
        this.intoDate = intoDate;
        this.transports = transports;
        this.partners = partners;
        this.state = state;
        this.transportType = transportType;
        this.position = position;
        this.collectionQRCode = collectionQRCode;
    }

    public String getStartCity() {
        if (StringUtil.isNotNull(getDistrict())) {
            return getDistrict();
        } else {
            return getCity();
        }
    }
}
