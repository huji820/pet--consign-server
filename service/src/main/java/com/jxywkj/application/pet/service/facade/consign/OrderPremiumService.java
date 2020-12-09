package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.OrderPremium;
import com.jxywkj.application.pet.model.consign.Staff;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName OrderPremiumService
 * @Description 差价
 * @Author LiuXiangLin
 * @Date 2019/8/23 17:04
 * @Version 1.0
 **/
public interface OrderPremiumService {
    /**
     * @Author LiuXiangLin
     * @Description 通过订编号查询所有的差价单
     * @Date 17:08 2019/8/23
     * @Param [orderNo]
     * @return java.util.List<com.jxywkj.application.pet.model.consign.OrderPremium>
     **/
    List<OrderPremium> listByOrderNo(String orderNo);

    /**
     * @Author LiuXiangLin
     * @Description 通过单号查询所有补差价总和
     * @Date 17:23 2019/8/23
     * @Param [orderNo]
     * @return java.math.BigDecimal
     **/
    BigDecimal getPriceDifferenceTotal(String orderNo);
    
    /**
     * @author LiuXiangLin
     * @description 生成一个补价单的记录
     * @date 15:59 2019/10/10
     * @param [orderPremium, staff]
     * @return int
     **/
    int insetOrderSpread(OrderPremium orderPremium, Staff staff);

}
