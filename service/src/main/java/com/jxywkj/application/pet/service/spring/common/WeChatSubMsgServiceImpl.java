package com.jxywkj.application.pet.service.spring.common;

import com.jxywkj.application.pet.common.enums.AppTypeEnum;
import com.jxywkj.application.pet.common.utils.wx.sub.InnerData;
import com.jxywkj.application.pet.common.utils.wx.sub.WeChatSubMsgDate;
import com.jxywkj.application.pet.common.utils.wx.sub.WeChatSubMsgUtil;
import com.jxywkj.application.pet.model.common.CustomerOpenId;
import com.jxywkj.application.pet.model.consign.Order;
import com.jxywkj.application.pet.model.consign.OrderState;
import com.jxywkj.application.pet.model.consign.OrderTransport;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.service.facade.common.CustomerOpenIdService;
import com.jxywkj.application.pet.service.facade.common.WeChatSubMsgService;
import com.jxywkj.application.pet.service.facade.consign.OrderStateService;
import com.jxywkj.application.pet.service.facade.consign.OrderTransportService;
import com.jxywkj.application.pet.service.facade.consign.StationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className WeChatSubMsgServiceImpl
 * @date 2020/6/5 10:31
 **/
@Service
public class WeChatSubMsgServiceImpl implements WeChatSubMsgService {
    @Resource
    WeChatSubMsgUtil weChatSubMsgUtil;

    @Resource
    CustomerOpenIdService customerOpenIdService;

    @Resource
    OrderTransportService orderTransportService;

    @Resource
    OrderStateService orderStateService;

    @Resource
    StationService stationService;

    /**
     * 揽件(服务状态更新提醒)
     */
    private static final String IN_PORT_TEMPLATE_ID = "6uwTclmrk9vYOT2AsWGLqOP5y0ZJsVsYW-stdcEkOSU";
    /**
     * 出港(发货提醒)
     */
    private static final String OUT_PORT_TEMPLATE_ID = "i_TmjhoPeu5x7L1OVrZapCcJo0rZKmXN9xjQCE7hBAc";

    /**
     * 到达(订单签收通知)
     */
    private static final String ARRIVE_TEMPLATE_ID = "0i3nHNkfOI1FwsuTSMfhAUnmbBzwB0c01R2Ef0Z7TcE";

    @Override
    public void inPort(Order order) throws Exception {
        // 封装数据对象
        Map<String, InnerData> paramMap = new HashMap<>(3);
        paramMap.put("character_string1", new InnerData(order.getOrderNo()));
        paramMap.put("name2", new InnerData(order.getSenderName()));
        paramMap.put("thing3", new InnerData("已揽件"));

        // 获取用户openid
        CustomerOpenId customerOpenId = customerOpenIdService.getByCustomerNoAndType(order.getCustomerNo(), AppTypeEnum.WE_APP_CONSIGN);

        if (customerOpenId != null) {
            // 发送订阅消息
            weChatSubMsgUtil.sendMsg(newWeChatSubMsgDate(paramMap, customerOpenId.getOpenId(), IN_PORT_TEMPLATE_ID));
        }
    }

    @Override
    public void outPort(Order order) throws Exception {
        // 获取订单运输明细
        OrderTransport orderTransport = orderTransportService.getLastByOrderNo(order.getOrderNo());

        // 获取用户openid
        CustomerOpenId customerOpenId = customerOpenIdService.getByCustomerNoAndType(order.getCustomerNo(), AppTypeEnum.WE_APP_CONSIGN);

        if (customerOpenId != null) {
            // 封装数据对象
            Map<String, InnerData> paramMap = new HashMap<>(5);
            paramMap.put("name1", new InnerData("宠物运输订单-" + order.getPetGenre().getPetGenreName()));
            paramMap.put("character_string10", new InnerData(order.getOrderNo()));
            paramMap.put("amount2", new InnerData(order.getPaymentAmount().toString()));
            paramMap.put("time3", new InnerData(order.getOrderDate() + " " + order.getOrderTime()));
            paramMap.put("character_string8", new InnerData(orderTransport == null ? null : orderTransport.getExpressNum()));

            // 发送订阅消息
            weChatSubMsgUtil.sendMsg(newWeChatSubMsgDate(paramMap, customerOpenId.getOpenId(), OUT_PORT_TEMPLATE_ID));
        }
    }

    @Override
    public void arrive(Order order) throws Exception {
        // 获取最后一个运输状态
        OrderState orderState = orderStateService.getLastOrderState(order.getOrderNo());

        // 获取最后一个站点
        Station station = stationService.getStation(orderState.getStation().getStationNo());

        // 获取运输单号
        OrderTransport orderTransport = orderTransportService.getLastByOrderNo(order.getOrderNo());

        // 获取openid
        CustomerOpenId customerOpenId = customerOpenIdService.getByCustomerNoAndType(order.getCustomerNo(), AppTypeEnum.WE_APP_CONSIGN);

        if (customerOpenId != null) {
            Map<String, InnerData> paramMap = new HashMap<>(4);
            paramMap.put("thing2", new InnerData("宠物运输订单-" + order.getPetGenre().getPetGenreName()));
            paramMap.put("character_string1", new InnerData(order.getOrderNo()));
            paramMap.put("character_string3", new InnerData(orderTransport == null ? null : orderTransport.getExpressNum()));
            paramMap.put("phrase4", new InnerData(station == null ? null : station.getStationName()));

            weChatSubMsgUtil.sendMsg(newWeChatSubMsgDate(paramMap, customerOpenId.getOpenId(), ARRIVE_TEMPLATE_ID));
        }
    }

    private WeChatSubMsgDate newWeChatSubMsgDate(Map<String, InnerData> paramMap, String openId, String templateId) {
        WeChatSubMsgDate result = new WeChatSubMsgDate();
        result.setDateMap(paramMap);
        result.setOpenId(openId);
        result.setTemplateId(templateId);

        return result;
    }
}
