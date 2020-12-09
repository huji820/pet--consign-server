package com.jxywkj.application.pet.common.enums;

/**
 * <p>
 * 临时减扣对象累心枚举
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className RebateBufferEnum
 * @date 2019/12/6 16:54
 **/
public enum RebateBufferEnum {
    /**
     * 返利
     */
    BUSINESS_WITHDRAW("withdraw", "t_business_withdraw", "商家提现"),
    STATION_WITHDRAW("withdraw", "t_station_withdraw", "站点提现");

    /**
     * 订单类型
     */
    private String billType;
    /**
     * 相关表
     */
    private String table;
    /**
     * 描述
     */
    private String describe;

    RebateBufferEnum(String billType, String table, String describe) {
        this.billType = billType;
        this.table = table;
        this.describe = describe;
    }

    public String getBillType() {
        return billType;
    }

    public String getTable() {
        return table;
    }

}
