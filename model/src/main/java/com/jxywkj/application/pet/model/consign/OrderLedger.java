package com.jxywkj.application.pet.model.consign;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jxywkj.application.pet.common.enums.TransportTypeEnum;
import com.jxywkj.application.pet.model.common.PetGenre;
import com.jxywkj.application.pet.model.common.PetSort;
import com.yangwang.sysframework.utils.TypeConvertUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description
 * @Author Administrator
 * @Date 2019-12-20 22:14
 * @Version 1.0
 */
@ApiModel(value = "订单账页表")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderLedger {

    @ApiModelProperty(value = "单据编号")
    String orderNo;

    @ApiModelProperty(value = "站点信息")
    Station station;

    @ApiModelProperty(value = "出发日期")
    String leaveDate;

    @ApiModelProperty(value = "下单日期")
    String orderDate;

    @ApiModelProperty(value = "下单时间")
    String orderTime;

    @ApiModelProperty(value = "起始城市")
    String startCity;

    @ApiModelProperty(value = "结束城市")
    String endCity;

    @ApiModelProperty(value = "运输方式")
    String transportTypeName;

    @ApiModelProperty(value = "宠物类别")
    PetSort petSort;

    @ApiModelProperty(value = "宠物分类")
    PetGenre petGenre;

    @ApiModelProperty(value = "宠物重量")
    BigDecimal weight;

    @ApiModelProperty(value = "宠物数量")
    int num;

    @ApiModelProperty(value = "发件人")
    String senderName;

    @ApiModelProperty(value = "发件人电话")
    String senderPhone;

    @ApiModelProperty(value = "收件人")
    String receiverName;

    @ApiModelProperty(value = "收件人电话")
    String receiverPhone;

    @ApiModelProperty(value = "支付金额")
    BigDecimal paymentAmount;

    @ApiModelProperty(value = "成本金额")
    BigDecimal costAmount;

    @ApiModelProperty(value = "毛利")
    BigDecimal profit;

    @ApiModelProperty(value = "是否同步")
    boolean synced;

    @ApiModelProperty(value = "是否支付")
    boolean paid;

    @ApiModelProperty(value = "订单创建时间")
    String dateCreate;

    @ApiModelProperty(value = "备注")
    String remarks;

    @ApiModelProperty(value = "宠物年龄")
    String petAge;

    public OrderLedger() {
    }

    public OrderLedger(Order order,Station station) {
        this.orderNo = order.getOrderNo();
        this.station = station;
        this.leaveDate = order.getLeaveDate();
        this.orderDate = order.getOrderDate();
        this.orderTime = order.getOrderTime();
        this.dateCreate = order.getOrderDate() + " " + order.getOrderTime();
        this.startCity = order.getTransport().getStartCity();
        this.endCity = order.getTransport().getEndCity();
        this.transportTypeName = getTransportTypeName(TypeConvertUtil.$int(order.getTransport().getTransportType()));
        this.petSort = order.getPetSort();
        this.petGenre = order.getPetGenre();
        this.weight = order.getWeight();
        this.num = order.getNum();
        this.senderName = order.getSenderName();
        this.senderPhone = order.getSenderPhone();
        this.receiverName = order.getReceiverName();
        this.receiverPhone = order.getReceiverPhone();
        this.paymentAmount = order.getPaymentAmount();
        this.costAmount = new BigDecimal(0);
        this.profit = paymentAmount.subtract(costAmount);
        this.synced = true;
        this.paid = true;
    }

    public OrderLedger(String orderNo, Station station, String leaveDate, String orderDate, String orderTime, String endCity, String transportTypeName, PetSort petSort, PetGenre petGenre, BigDecimal weight, int num, String senderName, String senderPhone, String receiverName, String receiverPhone, BigDecimal paymentAmount, BigDecimal costAmount, boolean synced, boolean paid) {
        this.orderNo = orderNo;
        this.station = station;
        this.leaveDate = leaveDate;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.endCity = endCity;
        this.transportTypeName = transportTypeName;
        this.petSort = petSort;
        this.petGenre = petGenre;
        this.weight = weight;
        this.num = num;
        this.senderName = senderName;
        this.senderPhone = senderPhone;
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
        this.paymentAmount = paymentAmount;
        this.costAmount = costAmount;
        this.profit = paymentAmount.subtract(costAmount);
        this.synced = synced;
        this.paid = paid;
    }

    public int getTransportType() {
        TransportTypeEnum transportTypeEnum = TransportTypeEnum.filterByTypeName(this.transportTypeName);
        return transportTypeEnum == null ? -1 : transportTypeEnum.getType();
    }

    public String getTransportTypeName(int transportType) {
        TransportTypeEnum transportTypeEnum = TransportTypeEnum.getTransportTypeEnum(transportType);
        return transportTypeEnum == null ? "" : transportTypeEnum.getName();
    }
}
