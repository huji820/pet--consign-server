package com.jxywkj.application.pet.model.consign;

import com.jxywkj.application.pet.model.consign.Order;
import com.jxywkj.application.pet.model.consign.Staff;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 运输单据退款
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className ConsignOrderRefund
 * @date 2019/11/27 9:20
 **/
@Data
@ApiModel(description = "运输单据退款")
public class ConsignOrderRefund {
    /**
     * 退款单号
     */
    String refundOrderNo;

    /**
     * 原单据
     */
    Order order;

    /**
     * 原单据金额
     */
    BigDecimal orderAmount;

    /**
     * 退款金额
     */
    BigDecimal refundAmount;

    /**
     * 手续费
     */
    BigDecimal serviceFeeAmount;

    /**
     * 操作员
     */
    Staff staff;

    /**
     * 退款时间
     */
    String refundTime;

    /**
     * 订单状态
     */
    String refundOrderState;

    /**
     * 退款理由
     */
    String refundReason;

}
