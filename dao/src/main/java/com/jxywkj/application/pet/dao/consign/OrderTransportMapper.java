package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.OrderTransport;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName OrderTransportMapper
 * @Description 订单航班
 * @Author LiuXiangLin
 * @Date 2019/9/23 14:32
 * @Version 1.0
 **/
@Component
public interface OrderTransportMapper {
    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 添加一条数据
     * @Date 14:32 2019/9/23
     * @Param [orderTransport]
     **/
    int saveOrderTransport(@Param("orderTransport") OrderTransport orderTransport);

    /**
     * <p>
     * 通过航班号以及开始时间查询订单航班
     * </p>
     *
     * @param transportNum 航班号
     * @param dateTime     出发时间
     * @return java.util.List<com.jxywkj.application.pet.model.consign.OrderTransport>
     * @author LiuXiangLin
     * @date 9:43 2019/12/11
     **/
    List<OrderTransport> listByTransportNumAndDateTime(@Param("transportNum") String transportNum, @Param("dateTime") String dateTime);

    /**
     * <p>
     * 查询最新一次的航班信息
     * </p>
     *
     * @param orderNo 订单编号
     * @return com.jxywkj.application.pet.model.consign.OrderTransport
     * @author LiuXiangLin
     * @date 18:20 2019/12/23
     **/
    OrderTransport getLastByOrderNo(@Param("orderNo") String orderNo);

}
