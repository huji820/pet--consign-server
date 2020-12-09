package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.Order;

import java.math.BigDecimal;

/**
 * @ClassName ConsignOrderPriceService
 * @Description: 获取订单支付价格Service
 * @Author GuoPengCheng
 * @Date 2020/7/27 17:45
 * @Version 1.0
 **/
public interface ConsignOrderPriceService {
    /**
     *
     * @return 订单支付价格
     * @Author GuoPengCheng
     * @Date 2020/7/27 17:45
     * @throws Exception
     */
    BigDecimal getOrderPaymentAmount(Order order)throws Exception;

}
