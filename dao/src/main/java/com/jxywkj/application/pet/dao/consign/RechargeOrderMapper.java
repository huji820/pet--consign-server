package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.RechargeOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName RechargeOrderMapper
 * @Description 用户充值
 * @Author LiuXiangLin
 * @Date 2019/8/19 11:37
 * @Version 1.0
 **/
@Mapper
public interface RechargeOrderMapper {
    /**
     * @Author LiuXiangLin
     * @Description 添加一个充值订单
     * @Date 11:38 2019/8/19
     * @Param [rechargeOrderMapper]
     * @return int
     **/
    int addRechargeOrder(@Param("rechargeOrder") RechargeOrder rechargeOrder);

    /**
     * @Author LiuXiangLin
     * @Description 通过单号查询数据
     * @Date 16:31 2019/8/19
     * @Param [orderNo]
     * @return com.jxywkj.application.pet.model.consign.RechargeOrder
     **/
    RechargeOrder getByOrderNo(@Param("orderNo")String orderNo);


    /**
     * @Author LiuXiangLin
     * @Description 更新订单状态
     * @Date 10:22 2019/8/20
     * @Param [orderNo, orderState]
     * @return int
     **/
    int updateOrderState(@Param("orderNo") String orderNo,@Param("orderState") String orderState);
    
}
