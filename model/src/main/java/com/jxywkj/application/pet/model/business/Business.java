package com.jxywkj.application.pet.model.business;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;


/**
 * <p>
 * 商家
 * </p>
 *
 * @author LiuXiangLin
 * @date 10:15 2020/3/16
 **/
@Data
@ApiModel(value = "商家")
public class Business {
    /**
     * 商家默认密码
     */
    public static final String PASSWORD = "999";

    @ApiModelProperty(value = "商家编号")
    String businessNo;

    @ApiModelProperty(value = "商家名称")
    String businessName;

    @ApiModelProperty(value = "注册时间")
    String registerTime;

    @ApiModelProperty(value = "起始营业时间")
    String startHours;

    @ApiModelProperty(value = "结束营业时间")
    String endHours;

    @ApiModelProperty(value = "联系人")
    String contact;

    @ApiModelProperty(value = "联系人电话")
    String contactPhone;

    @ApiModelProperty(value = "登录密码")
    String password;

    @ApiModelProperty(value = "描述")
    String describe;

    @ApiModelProperty(value = "是否缴纳保证金")
    Integer payBond;

    @ApiModelProperty(value = "认证类型")
    Integer authType;

    @ApiModelProperty(value = "粉丝数量")
    Integer fans;

    @ApiModelProperty(value = "关注数量")
    Integer followQty;

    @ApiModelProperty(value = "经度")
    Double longitude;

    @ApiModelProperty(value = "纬度")
    Double latitude;

    @ApiModelProperty(value = "省")
    String province;

    @ApiModelProperty(value = "市")
    String city;

    @ApiModelProperty(value = "区")
    String area;

    @ApiModelProperty(value = "详细地址")
    String detailAddress;

    @ApiModelProperty(value = "头像")
    String headImg;

    @ApiModelProperty(value = "状态")
    Integer state;

    @ApiModelProperty(value = "微信unionId")
    String unionId;

    @ApiModelProperty(value = "排序权重")
    Integer power;

    @ApiModelProperty(value = "信息是否完善")
    Integer complete;

    @ApiModelProperty(value = "积分")
    Integer credit;

    @ApiModelProperty(value = "等级")
    String level;

    @ApiModelProperty(value = "是否有服务，如果有服务,值为true")
    boolean haveService;

    @ApiModelProperty(value = "余额")
    BigDecimal balance;

    @ApiModelProperty(value = "顾客编号")
    String customerNo;

    @ApiModelProperty(value = "验证码")
    String verificationCode;

    @ApiModelProperty(value = "商家二维码图片")
    String weChatImg;

    @ApiModelProperty(value = "是否领取新客大礼包 0未领取 1已领取")
    String haveNewGiftBag;

    public Business() {
    }

    public Business(String businessNo, String businessName,
                    String registerTime, String startHours,
                    String endHours, String contact, String contactPhone,
                    String password, String describe, Integer payBond,
                    Integer authType, Integer fans, Double longitude,
                    Double latitude, String province,
                    String city, String area, String detailAddress,
                    String headImg, Integer state, String unionId, Integer power,
                    Integer followQty, Integer complete, Integer credit,
                    String level, String customerNo, String weChatImg, String haveNewGiftBag) {
        this.businessNo = businessNo;
        this.businessName = businessName;
        this.registerTime = registerTime;
        this.startHours = startHours;
        this.endHours = endHours;
        this.contact = contact;
        this.contactPhone = contactPhone;
        this.password = password;
        this.describe = describe;
        this.payBond = payBond;
        this.authType = authType;
        this.fans = fans;
        this.longitude = longitude;
        this.latitude = latitude;
        this.province = province;
        this.city = city;
        this.area = area;
        this.detailAddress = detailAddress;
        this.headImg = headImg;
        this.state = state;
        this.unionId = unionId;
        this.power = power;
        this.followQty = followQty;
        this.complete = complete;
        this.credit = credit;
        this.level = level;
        this.customerNo = customerNo;
        this.weChatImg = weChatImg;
        this.haveNewGiftBag = haveNewGiftBag;
    }

    public static class Builder {
        String businessNo;
        String businessName;
        String registerTime;
        String startHours;
        String endHours;
        String contact;
        String contactPhone;
        String password;
        String describe;
        Integer payBond;
        Integer authType;
        Integer fans;
        Double longitude;
        Double latitude;
        String province;
        String city;
        String area;
        String detailAddress;
        String headImg;
        Integer state;
        String unionId;
        Integer power;
        Integer followQty;
        Integer complete;
        Integer credit;
        String level;
        String customerNo;
        String weChatImg;
        String haveNewGiftBag;

        public Builder businessNo(String businessNo) {
            this.businessNo = businessNo;
            return this;
        }

        public Builder businessName(String businessName) {
            this.businessName = businessName;
            return this;
        }

        public Builder registerTime(String registerTime) {
            this.registerTime = registerTime;
            return this;
        }

        public Builder startHours(String startHours) {
            this.startHours = startHours;
            return this;
        }

        public Builder endHours(String endHours) {
            this.endHours = endHours;
            return this;
        }

        public Builder contact(String contact) {
            this.contact = contact;
            return this;
        }

        public Builder contactPhone(String contactPhone) {
            this.contactPhone = contactPhone;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder describe(String describe) {
            this.describe = describe;
            return this;
        }

        public Builder payBond(Integer payBond) {
            this.payBond = payBond;
            return this;
        }

        public Builder authType(Integer authType) {
            this.authType = authType;
            return this;
        }

        public Builder fans(Integer fans) {
            this.fans = fans;
            return this;
        }

        public Builder longitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder latitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder province(String province) {
            this.province = province;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder area(String area) {
            this.area = area;
            return this;
        }

        public Builder detailAddress(String detailAddress) {
            this.detailAddress = detailAddress;
            return this;
        }

        public Builder headImg(String headImg) {
            this.headImg = headImg;
            return this;
        }

        public Builder state(Integer state) {
            this.state = state;
            return this;
        }

        public Builder unionId(String unionId) {
            this.unionId = unionId;
            return this;
        }

        public Builder power(Integer power) {
            this.power = power;
            return this;
        }

        public Builder followQty(Integer followQty) {
            this.followQty = followQty;
            return this;
        }

        public Builder complete(Integer complete) {
            this.complete = complete;
            return this;
        }

        public Builder credit(Integer credit) {
            this.credit = credit;
            return this;
        }

        public Builder level(String level) {
            this.level = level;
            return this;
        }

        public Builder customerNo(String customerNo) {
            this.customerNo = customerNo;
            return this;
        }

        public Builder weChatImg(String weChatImg) {
            this.weChatImg = weChatImg;
            return this;
        }

        public Builder haveNewGiftBag(String haveNewGiftBag){
            this.haveNewGiftBag = haveNewGiftBag;
            return this;
        }

        public Business buildBusiness() {
            return new Business(this.businessNo, this.businessName,
                    this.registerTime, this.startHours,
                    this.endHours, this.contact, this.contactPhone,
                    this.password, this.describe, this.payBond,
                    this.authType, this.fans, this.longitude,
                    this.latitude, this.province,
                    this.city, this.area, this.detailAddress,
                    this.headImg, this.state, this.unionId,
                    this.power, this.followQty, this.complete,
                    this.credit, this.level, this.customerNo, this.weChatImg, this.haveNewGiftBag);
        }
    }
}
