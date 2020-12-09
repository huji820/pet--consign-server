package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.common.enums.MessageTypeEnum;
import com.jxywkj.application.pet.common.utils.CollectionUtils;
import com.jxywkj.application.pet.dao.consign.StationMessageMapper;
import com.jxywkj.application.pet.model.business.Business;
import com.jxywkj.application.pet.model.consign.*;
import com.jxywkj.application.pet.service.facade.consign.*;
import com.yangwang.sysframework.utils.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @ClassName StationMessageServiceImpl
 * @Description
 * @Author LiuXiangLin
 * @Date 2019/9/10 14:28
 * @Version 1.0
 **/
@Service
public class StationMessageServiceImpl implements StationMessageService {
    @Resource
    private StationMessageMapper stationMessageMapper;

    @Resource
    private StationService stationService;

    @Resource
    TransportService transportService;

    @Resource
    ConsignOrderService consignOrderService;


    @Override
    public int saveAnOrderMessage(Order order) {
        int result = 0;

        // 获取站点联系人
        //Station station = stationService.getStation(order.getTransport().getStation().getStationNo());
        Station station = stationService.getByOrderNo(order.getOrderNo());
        if (station != null) {
            StationMessage stationMessage = new StationMessage();
            setBaseStationMessage(stationMessage);
            stationMessage.setMessageTitle("您的站点有新的订单！");
            stationMessage.setMessageContent("您的站点有一个新订单！始发城市为：" +
                    order.getTransport().getStartCity() + "，终点城市为：" + order.getTransport().getEndCity() + "订单号为：" + order.getOrderNo()
                    + "请及时处理。");

            stationMessage.setReceiveNo(String.valueOf(station.getStationNo()));
            result += stationMessageMapper.addAStationMessage(stationMessage);

            // 获取终点站点
            Transport transport = transportService.getTransportByTransportNo(order.getTransport().getTransportNo());
            // 获取终点站点
            List<Station> stationList = stationService.listStation(transport.getEndCity());
            if (!CollectionUtils.isEmpty(stationList)) {
                stationMessage.setReceiveNo(String.valueOf(stationList.get(0).getStationNo()));
                result += stationMessageMapper.addAStationMessage(stationMessage);
            }
        }

        return result;
    }

    @Override
    public int confirmOrderStationMessage(Order order) {
        int result = 0;

        // 获取运输方式
        Transport transport = transportService.getTransportByTransportNo(order.getTransport().getTransportNo());

        if(transport == null){
             transport = transportService.getTransportByOrderNo(order.getOrderNo());
        }

        // 获取起始站点
        //Station station = stationService.getStation(order.getTransport().getStation().getStationNo());
        Station station = stationService.getByOrderNo(order.getOrderNo());
        StationMessage stationMessage = new StationMessage();
        setBaseStationMessage(stationMessage);
        stationMessage.setMessageTitle("订单签收提示");
        stationMessage.setMessageContent("您站点的订单：" + order.getOrderNo() + "已经被成功签收。");
        stationMessage.setReceiveNo(String.valueOf(station.getStationNo()));
        result += stationMessageMapper.addAStationMessage(stationMessage);


        // 获取终点站点
        List<Station> stationList = stationService.listStation(transport.getEndCity());

        if (!CollectionUtils.isEmpty(stationList)) {
            stationMessage.setReceiveNo(String.valueOf(stationList.get(0).getStationNo()));
            result += stationMessageMapper.addAStationMessage(stationMessage);
        }

        return result;
    }

    @Override
    public int saveApplyStaffMessage(Station station) {
        // 获取站点信息
        station = stationService.getStation(station.getStationNo());
        if (station != null) {
            // 开始发送站内信
            StationMessage stationMessage = new StationMessage();
            setBaseStationMessage(stationMessage);
            stationMessage.setReceiveNo(String.valueOf(station.getStationNo()));
            stationMessage.setMessageTitle("新员工申请提示");
            stationMessage.setMessageContent("您的站点有新员工申请，请在申请——>员工加入中查看具体信息。");

            return stationMessageMapper.addAStationMessage(stationMessage);
        }
        return 0;
    }

    @Override
    public int saveApplyBusinessMessage(Business business) {
        // 获取所在城市的所有站点
        List<Station> stationList = stationService.listStationByProvinceAndCity(business.getProvince(), business.getCity());
        int result = 0;
        if (!CollectionUtils.isEmpty(stationList)) {
            StationMessage stationMessage = new StationMessage();
            stationMessage.setMessageTitle("商户注册提示");
            stationMessage.setMessageContent("您的站点所在城市有新的商户注册，请在申请——>商家入驻中查看详情。");
            setBaseStationMessage(stationMessage);
            for (Station station : stationList) {
                stationMessage.setReceiveNo(String.valueOf(station.getStationNo()));
                result += stationMessageMapper.addAStationMessage(stationMessage);
            }
            return result;
        }
        return result;
    }

    @Override
    public int saveInPortMessage(Order order,Station currentStation,Station terminalStation) {
        int result = 0;

        StationMessage stationMessage = new StationMessage();
        setBaseStationMessage(stationMessage);

        stationMessage.setMessageTitle("入港提醒");
        stationMessage.setMessageContent("订单" + order.getOrderNo() + "已经入港。请注意及时关注。");

        // 获取运输录像
        Transport transport = transportService.getTransportByTransportNo(order.getTransport().getTransportNo());

        // 获取发货站点
        stationMessage.setReceiveNo(String.valueOf(currentStation.getStationNo()));
        result += stationMessageMapper.addAStationMessage(stationMessage);

        // 获取收货站点
        stationMessage.setReceiveNo(String.valueOf(terminalStation.getStationNo()));
        result += stationMessageMapper.addAStationMessage(stationMessage);

        return result;
    }

    @Override
    public int saveOutPortMessage(Order order,Station currentStation,Station terminalStation) {
        int result = 0;

        StationMessage stationMessage = new StationMessage();
        setBaseStationMessage(stationMessage);

        stationMessage.setMessageTitle("出港提醒");
        stationMessage.setMessageContent("订单" + order.getOrderNo() + "已经出港。请注意及时关注。");

        // 获取运输录像
        Transport transport = transportService.getTransportByTransportNo(order.getTransport().getTransportNo());

        // 获取发货站点
        stationMessage.setReceiveNo(String.valueOf(currentStation.getStationNo()));
        result += stationMessageMapper.addAStationMessage(stationMessage);

        if(terminalStation != null){
            // 获取收货站点
            stationMessage.setReceiveNo(String.valueOf(terminalStation.getStationNo()));
            result += stationMessageMapper.addAStationMessage(stationMessage);
        }

        return result;
    }

    @Override
    public int saveArriveMessage(Order order,Station currentStation) {
        int result = 0;

        StationMessage stationMessage = new StationMessage();
        setBaseStationMessage(stationMessage);

        stationMessage.setMessageTitle("到达提醒");
        stationMessage.setMessageContent("订单" + order.getOrderNo() + "已经达到终点站点。");

        // 获取运输录像
        Transport transport = transportService.getTransportByTransportNo(order.getTransport().getTransportNo());

        // 获取当前站点
        stationMessage.setReceiveNo(String.valueOf(currentStation.getStationNo()));
        result += stationMessageMapper.addAStationMessage(stationMessage);

        // 获取终点站点
        List<Station> stationList = stationService.listStation(transport.getEndCity());
        if (!CollectionUtils.isEmpty(stationList)) {
            stationMessage.setReceiveNo(String.valueOf(stationList.get(0).getStationNo()));
            result += stationMessageMapper.addAStationMessage(stationMessage);
        }

        return result;
    }

    @Override
    public int saveOrderContactsUpdateMessage(String orderNo) {
        // 获取订单
        Order order = consignOrderService.getConsignOrderByOrderNo(orderNo);

        if (order != null) {
            // 获取站点信息
            //Station station = stationService.getStation(order.getTransport().getStation().getStationNo());
            Station station = stationService.getByOrderNo(orderNo);
            if (station != null) {
                StationMessage stationMessage = new StationMessage();
                setBaseStationMessage(stationMessage);
                stationMessage.setReceiveNo(String.valueOf(station.getStationNo()));
                stationMessage.setMessageContent("订单" + orderNo + "更新了发件人或收件人，请及时处理。点击此处可查看订单明细。");
                return stationMessageMapper.addAStationMessage(stationMessage);
            }
        }

        return 0;
    }

    @Override
    public int countAllAdminUnreadByStationNo(int stationNo, String lastGetTime) {
        return stationMessageMapper.countAdminMsgByStationNo(stationNo, lastGetTime, MessageTypeEnum.UN_READ.getState());
    }

    @Override
    public List<StationMessage> listAllAdminMsgByStationNo(int stationNo, int offset, int limit) {
        return stationMessageMapper.listAdminMsgByStationNo(stationNo, offset, limit);
    }

    @Override
    public int saveOrderPremiumMessage(OrderPremium orderPremium) {
        // 获取站点信息
        Station station = stationService.getByOrderNo(orderPremium.getOrderNo());
        if (station != null) {
            StationMessage stationMessage = new StationMessage();
            setBaseStationMessage(stationMessage);
            stationMessage.setReceiveNo(String.valueOf(station.getStationNo()));
            stationMessage.setMessageTitle("补价支付成功通知");
            stationMessage.setMessageContent("订单" + orderPremium.getOrderNo()
                    + "的补价单:" + orderPremium.getBillNo()
                    + "已经支付完成，请及时处理。");

            return stationMessageMapper.addAStationMessage(stationMessage);
        }

        return 0;
    }

    @Override
    public int saveRebateMessage(Order order) {
        if (order == null) {
            return 0;
        }
        Station station = stationService.getByOrderNo(order.getOrderNo());
        StationMessage stationMessage = new StationMessage();
        setBaseStationMessage(stationMessage);
        stationMessage.setMessageTitle("到账提醒");
        stationMessage.setMessageContent("订单" + order.getOrderNo() + "的金额已经到账，请注意查收。");
        stationMessage.setReceiveNo(String.valueOf(station.getStationNo()));

        return stationMessageMapper.addAStationMessage(stationMessage);
    }

    @Override
    public int saveWithdrawSuccess(StationWithdraw stationWithdraw) {
        StationMessage stationMessage = new StationMessage();
        setBaseStationMessage(stationMessage);
        stationMessage.setReceiveNo(String.valueOf(stationWithdraw.getStation().getStationNo()));
        stationMessage.setMessageTitle("提现成功提醒");
        stationMessage.setMessageContent("您提现的￥" + stationWithdraw.getAmount() + "元已经到账，请注意查收。");

        return stationMessageMapper.addAStationMessage(stationMessage);
    }

    @Override
    public int saveRejectWithdraw(StationWithdraw stationWithdraw) {
        StationMessage stationMessage = new StationMessage();
        setBaseStationMessage(stationMessage);
        stationMessage.setReceiveNo(String.valueOf(stationWithdraw.getStation().getStationNo()));
        stationMessage.setMessageTitle("提现驳回提醒");
        stationMessage.setMessageContent("您提现的￥" + stationWithdraw.getAmount() + "元申请已被驳回。");

        return stationMessageMapper.addAStationMessage(stationMessage);
    }

    /**
     * @return void
     * @Author LiuXiangLin
     * @Description 设置基本数据
     * @Date 15:11 2019/9/30
     * @Param [stationMessage]
     **/
    private void setBaseStationMessage(StationMessage stationMessage) {
        stationMessage.setSendTime(DateUtil.format(new Date(), DateUtil.FORMAT_FULL));
        stationMessage.setUpdateTime(DateUtil.format(new Date(), DateUtil.FORMAT_FULL));
        stationMessage.setStatus(MessageTypeEnum.UN_READ.getState());
        stationMessage.setSendNo(null);
    }
}
