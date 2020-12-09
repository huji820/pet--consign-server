package com.jxywkj.application.pet.service.facade.common;


import com.jxywkj.application.pet.model.consign.Order;
import com.yangwang.sysframework.veriflight.dto.PushFlightResponseData;


/**
 * <p>
 * 短信发送service
 * </p>
 *
 * @author LiuXiangLin
 * @date 13:49 2020/5/18
 **/
public interface SmsSendService {
    /**
     * <p>
     * 发送订单揽件信息
     * </p>
     *
     * @param order 订单对象
     * @return void
     * @throws Exception
     * @author LiuXiangLin
     * @date 13:50 2020/5/18
     **/
    void sendInPortSms(Order order) throws Exception;


    /**
     * <p>
     * 发送飞常准短信
     * </p>
     *
     * @param order                  订单对象
     * @param pushFlightResponseData 非常准信息推送对象
     * @author LiuXiangLin
     * @date 13:54 2019/12/11
     **/
    void sendVariflightSms(Order order, PushFlightResponseData pushFlightResponseData);

    /**
     * <p>
     * 发送订单出港-大巴短信
     * </p>
     *
     * @param order 订单对象
     * @return void
     * @author LiuXiangLin
     * @date 13:53 2020/5/18
     **/
    void sendOutPortBusSms(Order order);

    /**
     * <p>
     * 发送订单出港-火车短信
     * </p>
     *
     * @param order 订单对象
     * @return void
     * @author LiuXiangLin
     * @date 13:53 2020/5/18
     **/
    void sendOutPortTrainSms(Order order);

    /**
     * <p>
     * 发送订单出港-飞机短信
     * </p>
     *
     * @param order 订单对象
     * @return void
     * @author LiuXiangLin
     * @date 13:54 2020/5/18
     **/
    void sendOutPortAirplaneSms(Order order);

    /**
     * <p>
     * 发送订单到达短信
     * </p>
     *
     * @param order 订单对象
     * @return void
     * @author LiuXiangLin
     * @date 13:54 2020/5/18
     **/
    void sendArrivedSms(Order order);

    /**
     * <p>
     * 发送出港信息
     * </p>
     *
     * @param order         订单对象
     * @param transportType 运输类型
     * @return void
     * @author LiuXiangLin
     * @date 14:21 2020/5/18
     **/
    void sendOutPortMsg(Order order, int transportType);

    /**
     * @Description: 发送出港消息到下一站点
     * @Author: zxj
     * @Date: 2020/10/8 16:50
     * @param order:
     * @param transportType:
     * @return: void
     **/
    void sendOutPortMsgToNextStation(Order order,int transportType,int stationNo) throws Exception;
    /**
     * <p>
     * 下单之后给站点员工的消息
     * </p>
     *
     * @param order 订单
     * @return void
     * @author LiuXiangLin
     * @date 17:05 2020/6/4
     **/
    void sendStationPlaceOrder(Order order);

    /**
     * <p>
     * 下单之后给用户发送的短信提示
     * </p>
     *
     * @param order 订单对象
     * @return void
     * @author LiuXiangLin
     * @date 17:05 2020/6/4
     **/
    void sendCustomerPlaceOrder(Order order);

    /**
     * @Description:  发送提现短信通知管理人员
     * @Author: zxj 
     * @Date: 2020/10/27 15:34
     * @param businessName: 提现申请人名称
     * @param withdrawNo:  提现申请编号
     * @return: void
     **/
    void sendWithdrawNotice(String businessName, String withdrawNo) throws Exception;
}
