package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.OrderPremium;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName OrderPremiumMapper
 * @Description 补差价
 * @Author LiuXiangLin
 * @Date 2019/8/23 17:14
 * @Version 1.0
 **/
public interface OrderPremiumMapper {
    /**
     * @Author LiuXiangLin
     * @Description 通过宠物订单号查询所有补差价单
     * @Date 17:15 2019/8/23
     * @Param [orderNo]
     * @return java.util.List<com.jxywkj.application.pet.model.consign.OrderPremium>
     **/
    List<OrderPremium> listByOrderNo(@Param("orderNo")String orderNo);

    /**
     * @Author LiuXiangLin
     * @Description 获取补差价总和
     * @Date 17:16 2019/8/23
     * @Param [orderNo]
     * @return java.math.BigDecimal
     **/
    BigDecimal getPriceDifferenceTotal(@Param("orderNo")String orderNo ,@Param("orderType") String orderType);

    /**
     * @author LiuXiangLin
     * @description 新增一条补价单
     * @date 15:25 2019/10/10
     * @param [orderPremium]
     * @return int
     **/
    int saveOrderSpread(OrderPremium orderPremium);

    /**
     * @author LiuXiangLin
     * @description 通过订单编号以及订单状态查询订单
     * @date 17:50 2019/10/10
     * @param [billNo, billState]
     * @return com.jxywkj.application.pet.model.consign.OrderPremium
     **/
    OrderPremium getByOrderNoAndOrderStatus(@Param("billNo") String billNo,@Param("billState") String billState);

    /**
     * @author LiuXiangLin
     * @description 通过差价单单号获取差价单
     * @date 9:09 2019/10/11
     * @param [billNo]
     * @return com.jxywkj.application.pet.model.consign.OrderPremium
     **/
    OrderPremium getByBillNo(String billNo);

    /**
     * @author LiuXiangLin
     * @description 更新差价单状态
     * @date 9:24 2019/10/11
     * @param [billNo, orderState]
     * @return int
     **/
    int updateOrderState(@Param("billNo") String billNo, @Param("orderState") String orderState);

    /**
     * 通过运输单号以及差价单类型查询差价单数量
     *
     * @param orderNo 运输单号
     * @param billType 差价单类型
     * @return int
     * @author LiuXiangLin
     * @date 15:44 2019/10/26
     **/
    int countByOrderNoAndType(@Param("orderNo") String orderNo,@Param("billType") String billType);
}
