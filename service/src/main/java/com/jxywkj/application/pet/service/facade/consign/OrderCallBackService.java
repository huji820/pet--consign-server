package com.jxywkj.application.pet.service.facade.consign;

/**
 * 订单支付回调Service
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className OrderCallBackService
 * @date 2019/10/31 10:37
 **/
public interface OrderCallBackService {
    /**
     * 运输订单支付回调
     *
     * @param orderNo 订单编号
     * @return int
     * @author LiuXiangLin
     * @date 10:39 2019/10/31
     **/
    int payConsignOrder(String orderNo) throws Exception;

    /**
     * 充值单支付回调
     *
     * @param orderNo 充值单单号
     * @return int
     * @author LiuXiangLin
     * @date 10:40 2019/10/31
     **/
    int payRechargeOrder(String orderNo);

    /**
     * 差价单支付回调
     *
     * @param billNo 差价单单号
     * @return int
     * @author LiuXiangLin
     * @date 10:40 2019/10/31
     **/
    int payPremiumOrder(String billNo);
}
