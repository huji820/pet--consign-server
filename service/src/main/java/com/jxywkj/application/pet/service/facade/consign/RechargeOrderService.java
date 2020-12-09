package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.RechargeOrder;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @ClassName RechargeOrderService
 * @Description 用户充值
 * @Author LiuXiangLin
 * @Date 2019/8/19 11:43
 * @Version 1.0
 **/
public interface RechargeOrderService {
    /**
     * <p>
     * 添加一条数据
     * </p>
     *
     * @param customerNo 用户编号
     * @param rechargeAmount 充值金额
     * @param appType app类型
     * @param userAddress 用户ip地址
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @author LiuXiangLin
     * @date 15:58 2020/3/4
     **/
    Map<String, String> addRechargeOrder(String customerNo, BigDecimal rechargeAmount, String appType, String userAddress) throws Exception;

    /**
     * @return com.jxywkj.application.pet.model.consign.RechargeOrder
     * @Author LiuXiangLin
     * @Description 通过单号查询数据
     * @Date 16:30 2019/8/19
     * @Param [orderNo]
     **/
    RechargeOrder getByOrderNo(String orderNo);


    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 修改订单状态
     * @Date 10:17 2019/8/20
     * @Param [orderNo, orderType]
     **/
    int updateOrderSate(String orderNo, String orderType);

}
