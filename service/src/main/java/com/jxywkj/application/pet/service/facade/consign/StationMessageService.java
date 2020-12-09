package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.business.Business;
import com.jxywkj.application.pet.model.consign.*;

import java.util.List;

/**
 * @ClassName StationMessageService
 * @Description 站点消息（站点消息，只作用于站点管理员）
 * @Author LiuXiangLin
 * @Date 2019/9/10 14:27
 * @Version 1.0
 **/
public interface StationMessageService {
    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 添加一条下单提示
     * @Date 14:31 2019/9/10
     * @Param [order]
     **/
    int saveAnOrderMessage(Order order);

    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 确认收货站点站内信
     * @Date 16:32 2019/9/10
     * @Param [order]
     **/
    int confirmOrderStationMessage(Order order);

    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 给站管理员添加一条新员工注册站内信
     * @Date 10:54 2019/9/20
     * @Param [station]
     **/
    int saveApplyStaffMessage(Station station);

    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 给商家注册添加一条消息
     * @Date 11:30 2019/9/20
     * @Param [business]
     **/
    int saveApplyBusinessMessage(Business business);

    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 入港站内信
     * @Date 15:08 2019/9/30
     * @Param [order,currentStation:当前站点,terminalStation:下一站点]
     **/
    int saveInPortMessage(Order order,Station currentStation,Station terminalStation);

    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 添加出港站内信
     * @Date 15:21 2019/9/30
     * @Param [order,currentStation:发货站点,terminalStation:收货站点]
     **/
    int saveOutPortMessage(Order order,Station currentStation,Station terminalStation);

    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 到达提示
     * @Date 15:52 2019/9/30
     * @Param [order]
     **/
    int saveArriveMessage(Order order,Station currentStation);

    /**
     * @param orderNo 订单编号
     * @return int 修改条数
     * @author LiuXiangLin
     * @description 如果订单更细了联系人则给站点联系人发送一条站内信
     * @date 10:00 2019/10/14
     **/
    int saveOrderContactsUpdateMessage(String orderNo);

    /**
     * @param  stationNo, lastGetTime
     * @return int
     * @author LiuXiangLin
     * @description 通过站点编号查询管理员未读所有条数（包括个人信息）
     * @date 14:19 2019/10/14
     **/
    int countAllAdminUnreadByStationNo(int stationNo, String lastGetTime);

    /**
     * @param stationNo
     * @return java.util.List<com.jxywkj.application.pet.model.consign.StationMessage>
     * @author LiuXiangLin
     * @description 通过站点编号查询所有管理员的站内信（包括个人信息）
     * @date 14:40 2019/10/14
     **/
    List<StationMessage> listAllAdminMsgByStationNo(int stationNo, int offset, int limit);

    /**
     * @param orderPremium
     * @return int
     * @author LiuXiangLin
     * @description 客户支付补价单后发送的推送
     * @date 16:04 2019/10/14
     **/
    int saveOrderPremiumMessage(OrderPremium orderPremium);

    /**
     * <p>
     * 发送返利到账站内信
     * </p>
     *
     * @param order 订单
     * @return int
     * @author LiuXiangLin
     * @date 9:59 2019/12/9
     **/
    int saveRebateMessage(Order order);

    /**
     * <p>
     * 提现成功提醒
     * </p>
     *
     * @param stationWithdraw 提现对象
     * @return int
     * @author LiuXiangLin
     * @date 10:15 2019/12/9
     **/
    int saveWithdrawSuccess(StationWithdraw stationWithdraw);

    /**
     * <p>
     * 提现被驳回提醒
     * </p>
     *
     * @param stationWithdraw 提现对象
     * @return int
     * @author LiuXiangLin
     * @date 10:16 2019/12/9
     **/
    int saveRejectWithdraw(StationWithdraw stationWithdraw);

}
