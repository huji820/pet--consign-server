package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.common.enums.TransportTypeEnum;
import com.jxywkj.application.pet.common.utils.StringUtils;
import com.jxywkj.application.pet.model.consign.Order;
import com.jxywkj.application.pet.model.consign.OrderTransport;
import com.jxywkj.application.pet.model.consign.Transport;
import com.jxywkj.application.pet.service.facade.common.CustomerMessageService;
import com.jxywkj.application.pet.service.facade.common.SmsSendService;
import com.jxywkj.application.pet.service.facade.consign.*;
import com.yangwang.sysframework.utils.TypeConvertUtil;
import com.yangwang.sysframework.veriflight.VariFlightUtil;
import com.yangwang.sysframework.veriflight.dto.PushFlightResponseData;
import com.yangwang.sysframework.veriflight.dto.TokenRequestData;
import com.yangwang.sysframework.veriflight.dto.TokenResponseData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className VariFlightServiceImpl
 * @date 2019/12/7 17:45
 **/
@Service
public class VariFlightServiceImpl implements VariFlightService {
    @Resource
    VariFlightUtil variFlightUtil;

    @Value("${variflight.appid}")
    String appid;

    @Value("${variflight.appsecurity}")
    String appsecurity;

    @Resource
    TransportService transportService;

    @Resource
    OrderTransportService orderTransportService;

    @Resource
    OrderStateService orderStateService;

    @Resource
    SmsSendService smsSendService;

    @Resource
    CustomerMessageService customerMessageService;

    @Resource
    ConsignOrderService consignOrderService;

    /**
     * 空格
     */
    private static final String SPACE = " ";

    @Override
    public int orderSubscribe(Order order) {
        // 获取运输路线
        Transport transport = transportService.getTransportByTransportNo(order.getTransport().getTransportNo());

        // 运输路线类别判断
        if (transport == null ||
                TransportTypeEnum.RAILWAY.getType() == TypeConvertUtil.$int(transport.getTransportType())
                || TransportTypeEnum.SPECIAL_TRAIN.getType() == TypeConvertUtil.$int(transport.getTransportType())
                || TransportTypeEnum.BUS.getType() == TypeConvertUtil.$int(transport.getTransportType())
        ) {
            return 0;
        }

        // 获取最新的航班情况
        OrderTransport orderTransport = orderTransportService.getLastByOrderNo(order.getOrderNo());
        if (orderTransport == null) {
            return 0;
        }

        // 数据判断
        if (StringUtils.isBlank(orderTransport.getStartCity())
                || StringUtils.isBlank(orderTransport.getEndCity())
                || StringUtils.isBlank(orderTransport.getDateTime())
                || StringUtils.isBlank(orderTransport.getTransportNum())) {
            return 0;
        }

        // 开始订阅
        TokenRequestData tokenRequestData = new TokenRequestData();
        tokenRequestData.setAppid(appid);
        tokenRequestData.setAppsecurity(appsecurity);

        tokenRequestData.setFnum(orderTransport.getTransportNum());
        tokenRequestData.setDep(TypeConvertUtil.$Str(orderTransport.getStartCity()).toUpperCase());
        tokenRequestData.setArr(TypeConvertUtil.$Str(orderTransport.getEndCity()).toUpperCase());
        tokenRequestData.setDate(orderTransport.getDateTime());

        try {
            TokenResponseData responseData = variFlightUtil.subscribe(tokenRequestData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int orderPush(PushFlightResponseData responseData) {
        // 开始时间空判断
        if (StringUtils.isBlank(responseData.getFlightDeptimePlanDate())) {
            return 0;
        }

        // 出发时间
        String[] startTimeArray = responseData.getFlightDeptimePlanDate().split(SPACE);

        if (startTimeArray.length > 0) {
            // 获取订单列表
            List<OrderTransport> orderTransports = orderTransportService.listByTransportNumAndDateTime(responseData.getFlightNo(), responseData.getFlightDeptimePlanDate().substring(0, 10));
            Order order;
            if (orderTransports != null && !orderTransports.isEmpty()) {
                for (OrderTransport orderTransport : orderTransports) {
                    order = consignOrderService.getConsignOrderByOrderNo(orderTransport.getOrder().getOrderNo());

                    if (order == null) {
                        continue;
                    }

                    try {
                        // 发送短信
                        smsSendService.sendVariflightSms(order, responseData);
                        // 发送站内信
                        customerMessageService.sendVariflight(order, responseData);
                        // 更新订单状态
                        orderStateService.updateVariflightState(order, responseData);
                    } catch (Exception e) {
                        System.err.println("发送飞常准推送失败！订单号为：" + order.getOrderNo());
                        e.printStackTrace();
                    }
                }
            }
        }
        return 0;
    }
}
