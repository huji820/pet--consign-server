package com.jxywkj.application.pet.model.consign;

import com.jxywkj.application.pet.model.common.PetGenre;
import com.jxywkj.application.pet.model.common.PetSort;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author huji
 * @Date 2019-06-19 0:14
 * @table t_consign_order
 * @Version 1.0
 */
@ApiModel(value = "订单表")
@Data
public class Order {

    @ApiModelProperty(value = "订单编号")
    String orderNo;

    @ApiModelProperty(value = "选择的运输线路")
    Transport transport;

    @ApiModelProperty(value = "出发日期")
    String leaveDate;

    @ApiModelProperty(value = "下单日期")
    String orderDate;

    @ApiModelProperty(value = "下单时间")
    String orderTime;

    @ApiModelProperty(value = "宠物种类")
    PetSort petSort;

    @ApiModelProperty(value = "宠物类型")
    PetGenre petGenre;

    @ApiModelProperty(value = "宠物重量")
    BigDecimal weight;

    @ApiModelProperty(value = "数量")
    int num;

    @ApiModelProperty(value = "成交的价格")
    BigDecimal finalRetailPrice;

    @ApiModelProperty(value = "商家的底价")
    BigDecimal finalJoinPrice;

    @ApiModelProperty("最终付款金额")
    BigDecimal paymentAmount;

    @ApiModelProperty(value = "宠物笼具")
    AddedWeightCage addedWeightCage;

    @ApiModelProperty(value = "保价")
    AddedInsure addedInsure;

    @ApiModelProperty("宠物金额")
    BigDecimal petAmount;

    @ApiModelProperty("上门接宠地址")
    String receiptAddress;

    @ApiModelProperty("上门接宠经度")
    String receiptLongitude;

    @ApiModelProperty("上门接宠纬度")
    String receiptLatitude;

    @ApiModelProperty("上门接宠距离")
    BigDecimal receiptDistance;

    @ApiModelProperty("上门接宠花费金额")
    BigDecimal receiptAmount;

    @ApiModelProperty("送宠到家地址")
    String sendAddress;

    @ApiModelProperty("送宠到家经度")
    String sendLongitude;

    @ApiModelProperty("送宠到家纬度")
    String sendLatitude;

    @ApiModelProperty("送宠到家距离")
    BigDecimal sendDistance;

    @ApiModelProperty("送宠到家花费金额")
    BigDecimal sendAmount;

    @ApiModelProperty(value = "寄件人姓名")
    String senderName;

    @ApiModelProperty(value = "寄件人联系电话")
    String senderPhone;

    @ApiModelProperty(value = "收件人姓名")
    String receiverName;

    @ApiModelProperty(value = "收件人电话")
    String receiverPhone;

    @ApiModelProperty(value = "备注")
    String remarks;

    @ApiModelProperty(value = "状态")
    String state;

    @ApiModelProperty("是否赠送食物")
    String giveFood;

    @ApiModelProperty("是否担保")
    String guarantee;

    @ApiModelProperty("订单实时路线")
    List<OrderState> orderStates;

    @ApiModelProperty("customerNo")
    String customerNo;

    @ApiModelProperty(value = "小程序模板消息必须参数")
    String prepayId;

    @ApiModelProperty(value = "订单额外备注")
    List<OrderRemarks> orderRemarksList;

    @ApiModelProperty(value = "订单已分配人员")
    List<OrderAssignment> orderAssignments;

    @ApiModelProperty(value = "临时分配送宠")
    List<OrderTempDeliver> orderTempDelivers;

    @ApiModelProperty(value = "差价单")
    List<OrderPremium> orderPremiumList;

    @ApiModelProperty(value = "订单运输编号")
    OrderTransport orderTransport;

    @ApiModelProperty(value = "订单配送详情")
    OrderTakeDetail orderTakeDetail;

    @ApiModelProperty(value = "分享人openId")
    String shareOpenId;

    @ApiModelProperty(value = "宠物年龄")
    String petAge;

    @ApiModelProperty(value = "微信商户订单号")
    String outTradeNo;

    @ApiModelProperty(value = "订单实时状态最新sn")
    Integer maxStatusSn;

    @ApiModelProperty(value = "推荐人名字")
    String recommendName;

    @ApiModelProperty(value = "推荐人电话")
    String recommendPhone;

    @ApiModelProperty(value = "推荐人备注信息")
    String recommendRemarks;

    @ApiModelProperty(value = "支付价格类型")
    String payAmountType;

    @ApiModelProperty(value = "所属站点")
    Station station;

    @ApiModelProperty(value = "付款凭证")
    String paymentVoucher;

    @ApiModelProperty(value = "审核付款凭证反馈")
    String reviewVoucherRemarks;

    @ApiModelProperty(value = "线下支付标识 1为线下支付")
    String offlinePaymentLogo;

    @ApiModelProperty(value = "确认条例标识 0未确认 1已确认")
    String confirmationRegulation;

    @ApiModelProperty(value = "订单归属员工")
    Staff staff;

    public Order() {
    }


    public Order(String orderNo) {
        this.orderNo = orderNo;
    }

    public Order(String orderNo, Transport transport, String leaveDate, String orderDate, String orderTime, PetSort petSort, PetGenre petGenre, BigDecimal weight, int num, BigDecimal finalRetailPrice, BigDecimal finalJoinPrice) {
        this.orderNo = orderNo;
        this.transport = transport;
        this.leaveDate = leaveDate;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.petSort = petSort;
        this.petGenre = petGenre;
        this.weight = weight;
        this.num = num;
        this.finalRetailPrice = finalRetailPrice;
        this.finalJoinPrice = finalJoinPrice;
    }


    /**
     * <p>
     * 是否参与投保
     * </p>
     *
     * @return boolean
     * @author LiuXiangLin
     * @date 11:25 2019/12/31
     **/
    public boolean joinInsure() {
        return this.getAddedInsure() != null && this.getAddedInsure().getInsureAmount().compareTo(BigDecimal.ZERO) > 0;
    }
}
