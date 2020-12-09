package com.jxywkj.application.pet.common.enums;

/**
 * <p>
 * 余额流水类型
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className BalanceFlowTypeEnum
 * @date 2019/12/28 16:33
 **/
public enum BalanceFlowTypeEnum {
    /**
     * 站点下单
     */
    CONSING_ORDER_STATION("orderStation", "下单站点金额"),
    /**
     * 分享客户下单返利
     */
    CONSING_ORDER_REBATE("orderRebate", "下单分享返利"),
    /**
     * 提现发起
     */
    WITHDRAW_CONFIRM("withdrawConfirm", "余额提现发起"),
    /**
     * 提现驳回
     */
    WITHDRAW_REJECT("withdrawReject", "余额提现驳回"),
    /**
     * 订单补差价
     */
    CONSING_ORDER_PREMIUM("orderPremium", "订单补差价"),
    /**
     * 订单退款
     */
    CONSIGN_ORDER_REFUND("orderRefund", "订单退款"),
    /**
     * 保价流水
     */
    CONSIGN_ORDER_INSURE("orderInsure", "保价流水"),
    /**
     * 充值
     */
    RECHARGE("recharge", "余额充值");

    /**
     * 类型
     */
    String type;
    /**
     * 描述
     */
    String typeDescribe;

    BalanceFlowTypeEnum(String type, String typeDescribe) {
        this.type = type;
        this.typeDescribe = typeDescribe;
    }

    public String getType() {
        return type;
    }

    public String getTypeDescribe() {
        return typeDescribe;
    }}
