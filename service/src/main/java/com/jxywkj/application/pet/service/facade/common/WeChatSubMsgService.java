package com.jxywkj.application.pet.service.facade.common;

import com.jxywkj.application.pet.model.consign.Order;

/**
 * <p>
 * 微信订阅消息
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className WeChatSubMsgService
 * @date 2020/6/5 10:18
 **/
public interface WeChatSubMsgService {
    /**
     * <p>
     * 揽件
     * </p>
     *
     * @param order 订单对象
     * @return void
     * @author LiuXiangLin
     * @date 10:21 2020/6/5
     **/
    void inPort(Order order) throws Exception;

    /**
     * <p>
     * 出港
     * </p>
     *
     * @param order 订单对象
     * @return void
     * @author LiuXiangLin
     * @date 10:28 2020/6/5
     **/
    void outPort(Order order) throws Exception;

    /**
     * <p>
     * 到达
     * </p>
     *
     * @param order 订单对象
     * @return void
     * @author LiuXiangLin
     * @date 10:31 2020/6/5
     **/
    void arrive(Order order) throws Exception;
}
