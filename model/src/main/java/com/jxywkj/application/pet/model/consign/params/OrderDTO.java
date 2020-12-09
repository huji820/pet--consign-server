package com.jxywkj.application.pet.model.consign.params;

import com.jxywkj.application.pet.model.consign.OrderLedger;
import com.jxywkj.application.pet.model.consign.Transport;
import com.yangwang.sysframework.utils.TypeConvertUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName OrderDTO
 * @Description
 * @Author LiuXiangLin
 * @Date 2019/7/17 11:47
 * @Version 1.0
 **/
@Data
@ApiModel(value = "订单Service参数对象")
public class OrderDTO {
    @ApiModelProperty(value = "开始城市")
    String startCity;
    @ApiModelProperty(value = "目的城市")
    String endCity;
    @ApiModelProperty(value = "出发时间")
    String leaveDate;
    @ApiModelProperty(value = "宠物数量")
    int num;
    @ApiModelProperty(value = "宠物类别")
    String petType;
    @ApiModelProperty(value = "宠物类型")
    String petClassify;
    @ApiModelProperty(value = "宠物重量")
    BigDecimal weight;
    @ApiModelProperty(value = "运输方式")
    int transportType;
    @ApiModelProperty(value = "购买宠物笼")
    int buyPetCage;
    @ApiModelProperty(value = "上门接宠物地址")
    String receiptAddress;
    @ApiModelProperty(value = "上门接宠经度")
    String receiptLongitude;
    @ApiModelProperty(value = "上门接宠纬度")
    String receiptLatitude;
    @ApiModelProperty(value = "上门送宠物地址")
    String sendAddress;
    @ApiModelProperty(value = "送宠到家纬度")
    String sendLatitude;
    @ApiModelProperty(value = "送宠到家经度")
    String sendLongitude;
    @ApiModelProperty(value = "送宠到家距离")
    BigDecimal sendDistance;
    @ApiModelProperty(value = "宠物总金额")
    BigDecimal petAmount;
    @ApiModelProperty(value = "发宠人姓名")
    String senderName;
    @ApiModelProperty(value = "发宠人电话")
    String senderPhone;
    @ApiModelProperty(value = "收宠人姓名")
    String receiverName;
    @ApiModelProperty(value = "收宠人电话")
    String receiverPhone;
    @ApiModelProperty(value = "customerNo")
    String customerNo;
    @ApiModelProperty(value = "备注")
    String remarks;
    @ApiModelProperty(value = "猫猫套餐 0:不需要 1:需要")
    Integer giveFood;
    @ApiModelProperty(value = "是否需要担保")
    Integer guarantee;
    @ApiModelProperty(value = "分享人openId")
    String shareOpenId;
    @ApiModelProperty(value = "宠物年龄")
    String petAge;
    @ApiModelProperty(value = "推荐人名字")
    String recommendName;
    @ApiModelProperty(value = "推荐人电话")
    String recommendPhone;
    @ApiModelProperty(value = "推荐人备注信息")
    String recommendRemarks;
    @ApiModelProperty(value = "支付价格类型")
    String payAmountType;
    @ApiModelProperty(value = "自定义价格")
    BigDecimal customPrice;
    @ApiModelProperty(value = "站点编号")
    String startStationNo;

    public OrderDTO() {
    }

    public OrderDTO(OrderLedger orderLedger, Transport transport) {
        this.startCity = orderLedger.getStartCity()==null?orderLedger.getStation().getStartCity():orderLedger.getStartCity();
        this.endCity = orderLedger.getEndCity();
        this.leaveDate = orderLedger.getLeaveDate();
        this.weight = orderLedger.getWeight();
        this.num = orderLedger.getNum();
        this.petType = orderLedger.getPetSort().getPetSortName();
        this.petClassify = orderLedger.getPetGenre().getPetGenreName();
        this.senderName = orderLedger.getSenderName();
        this.senderPhone = orderLedger.getSenderPhone();
        this.remarks = orderLedger.getRemarks();
        this.receiverName = orderLedger.getReceiverName();
        this.receiverPhone = orderLedger.getReceiverPhone();
        this.transportType = TypeConvertUtil.$int(transport.getTransportType());
    }
}
