package com.jxywkj.application.pet.service.facade.consign;

/**
 * <p>
 * 宠物运输单据
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className ConsignOrderTaskService
 * @date 2019/11/21 15:09
 **/
public interface ConsignOrderTaskService {
    /**
     * <p>
     * 检查订单情况
     * 如果有已经支付但是未回写订单的，则回写订单
     * 否则取消该订单
     * </p>
     *
     * @author LiuXiangLin
     * @date 15:10 2019/11/21
     **/
    void checkConsignOrder();

    /**
     * <p>
     * 自动确认订单
     * 出港之后超过48小时之后就自动确认收货
     * </p>
     *
     * @author LiuXiangLin
     * @date 17:04 2019/12/30
     **/
    void automaticConfirmConsignOrder();

    /**
     * <p>
     * 自动取消未支付的订单
     * 下单之后超过24小时
     * </p>
     *
     * @param
     * @return void
     * @author LiuXiangLin
     * @date 9:34 2020/1/2
     **/
    void cancelUnpaidOrder();
}
