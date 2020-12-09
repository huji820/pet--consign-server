package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.OrderTransport;

import java.util.List;

/**
 * @ClassName OrderTransportServiceImpl
 * @Description 订单航班
 * @Author LiuXiangLin
 * @Date 2019/9/23 14:35
 * @Version 1.0
 **/
public interface OrderTransportService {
    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 添加一条数据
     * @Date 14:36 2019/9/23
     * @Param [orderTransport]
     **/
    int saveOrderTransport(OrderTransport orderTransport);

    /**
     * <p>
     * 通过航班号以及出发时间查询订单航班
     * </p>
     *
     * @param transportNum 航班号码
     * @param startTime    出发时间
     * @return java.util.List<com.jxywkj.application.pet.model.consign.OrderTransport>
     * @author LiuXiangLin
     * @date 9:41 2019/12/11
     **/
    List<OrderTransport> listByTransportNumAndDateTime(String transportNum, String startTime);

    /**
     * <p>
     * 查询最新一次的航班信息
     * </p>
     *
     * @param orderNo 订单编号
     * @return com.jxywkj.application.pet.model.consign.OrderTransport
     * @author LiuXiangLin
     * @date 18:19 2019/12/23
     **/
    OrderTransport getLastByOrderNo(String orderNo);

}
