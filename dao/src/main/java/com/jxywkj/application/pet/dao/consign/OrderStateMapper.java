package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.OrderState;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @ClassName OrderStateMapper
 * @Description 订单当前状态
 * @Author LiuXiangLin
 * @Date 2019/7/22 14:49
 * @Version 1.0
 **/
@Mapper
public interface OrderStateMapper {

    /**
     * 查询所有的订单状态
     * @param orderNo
     * @return
     */
    List<OrderState> listOrderState(String orderNo);

    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 查询该订单的所有的位置数据条数
     * @Date 16:53 2019/7/22
     * @Param [orderNo]
     **/
    int countByOrderNo(@Param("orderNo") String orderNo);

    /**
     * @return List<OrderState>
     * @Author zhouxiaojian
     * @Description 查询该订单的所有的位置数据
     * @param orderNo
     * @Date 9:02 2020/7/23
     */
    List<OrderState> selectByOrderNo(@Param("orderNo")String orderNo);

    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 添加一条数据
     * @Date 11:17 2019/8/9
     * @Param [orderState]
     **/
    int saveOrderState(@Param("orderState") OrderState orderState);


    /**
     * @return com.jxywkj.application.pet.model.consign.OrderState
     * @Author LiuXiangLin
     * @Description 获取订单的最后的一个状态
     * @Date 16:35 2019/8/12
     * @Param [orderNo]
     **/
    OrderState getLastStateByOrderState(@Param("orderNo") String orderNo);


    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 获取最大的sn
     * @Date 15:08 2019/8/16
     * @Param [orderNo]
     **/
    int getLastSn(@Param("orderNo") String orderNo);


    
    /**
     * @Author LiuXiangLin
     * @Description 通过订单号和查询最后一个状态
     * @Date 11:21 2019/9/21
     * @Param [orderNo, type]
     * @return com.jxywkj.application.pet.model.consign.OrderState
     **/
    OrderState getLastByOrderNoAndState(@Param("orderNo") String orderNo, @Param("types") String[] type);

}
