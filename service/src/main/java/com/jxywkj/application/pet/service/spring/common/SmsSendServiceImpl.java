package com.jxywkj.application.pet.service.spring.common;

import com.jxywkj.application.pet.common.enums.TransportTypeEnum;
import com.jxywkj.application.pet.common.enums.UserRolesEnum;
import com.jxywkj.application.pet.common.utils.AliSmsUtils;
import com.jxywkj.application.pet.model.admin.Manager;
import com.jxywkj.application.pet.model.common.Customer;
import com.jxywkj.application.pet.model.consign.*;
import com.jxywkj.application.pet.service.facade.admin.ManagerService;
import com.jxywkj.application.pet.service.facade.common.CustomerService;
import com.jxywkj.application.pet.service.facade.common.SmsSendService;
import com.jxywkj.application.pet.service.facade.consign.*;
import com.yangwang.sysframework.sms.SmsUtil;
import com.yangwang.sysframework.sms.TemplateData;
import com.yangwang.sysframework.utils.TypeConvertUtil;
import com.yangwang.sysframework.veriflight.dto.PushFlightResponseData;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 短信发送
 * </p>
 *
 * @author LiuXiangLin
 * @date 13:50 2020/5/18
 **/
@Service
public class SmsSendServiceImpl implements SmsSendService {
    @Resource
    TransportService transportService;

    @Resource
    StationService stationService;

    @Resource
    OrderTransportService orderTransportService;

    @Resource
    OrderTakeDetailService orderTakeDetailService;

    @Resource
    AliSmsUtils aliSmsUtils;

    @Resource
    StaffService staffService;

    @Resource
    CustomerService customerService;

    @Resource
    ManagerService managerService;

    @Override
    public void sendInPortSms(Order order) {
        // 获取运输路线
        Transport transport = transportService.getTransportByTransportNo(order.getTransport().getTransportNo());

        if(transport==null){
            transport = transportService.getTransportByOrderNo(order.getOrderNo());
        }

        //通过订单获取站点
        Station station = stationService.getByOrderNo(order.getOrderNo());

        TemplateData templateData = new TemplateData();
        templateData.put("startCity", transport.getStartCity());
        templateData.put("endCity", transport.getEndCity());
        templateData.put("transportName", station.getStationName());
        templateData.put("stationPhone", station.getPhone());
        templateData.put("orderNo", order.getOrderNo());

        // 发件人
        try {
            templateData.put("userName", order.getSenderName());
            aliSmsUtils.sendSms(templateData, order.getSenderPhone(), "SMS_192570060");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 收件人
        try {
            templateData.put("userName", order.getReceiverName());
            aliSmsUtils.sendSms(templateData, order.getReceiverPhone(), "SMS_192570060");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendVariflightSms(Order order, PushFlightResponseData pushFlightResponseData) {
        TemplateData templateData = new TemplateData();
        templateData.put("flightCompany", pushFlightResponseData.getFlightCompany());
        templateData.put("flightNo", pushFlightResponseData.getFlightNo());
        templateData.put("flightDeptimePlanDate", pushFlightResponseData.getFlightDeptimePlanDate());
        templateData.put("flightArrtimePlanDate", pushFlightResponseData.getFlightArrtimePlanDate());
        templateData.put("FlightState", pushFlightResponseData.getFlightState());

        // 发件人
        try {
            aliSmsUtils.sendSms(templateData, order.getSenderPhone(), "SMS_190272901");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 收件人
        try {
            aliSmsUtils.sendSms(templateData, order.getReceiverPhone(), "SMS_190272901");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendOutPortBusSms(Order order) {
        TemplateData templateData = newOutPortTemplateData(order);

        // 发件人
        try {
            templateData.put("userName", order.getSenderName());
            aliSmsUtils.sendSms(templateData, order.getSenderPhone(), "SMS_192575131");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 收件人
        try {
            templateData.put("userName", order.getReceiverName());
            aliSmsUtils.sendSms(templateData, order.getReceiverPhone(), "SMS_192575131");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void sendOutPortTrainSms(Order order) {
        TemplateData templateData = newOutPortTemplateData(order);

        // 发件人
        try {
            templateData.put("userName", order.getSenderName());
            aliSmsUtils.sendSms(templateData, order.getSenderPhone(), "SMS_192530100");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 收件人
        try {
            templateData.put("userName", order.getReceiverName());
            aliSmsUtils.sendSms(templateData, order.getReceiverPhone(), "SMS_192530100");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendOutPortAirplaneSms(Order order) {
        TemplateData templateData = newOutPortTemplateData(order);

        // 发件人
        try {
            templateData.put("userName", order.getSenderName());
            aliSmsUtils.sendSms(templateData, order.getSenderPhone(), "SMS_192540215");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 收件人
        try {
            templateData.put("userName", order.getReceiverName());
            aliSmsUtils.sendSms(templateData, order.getReceiverPhone(), "SMS_192540215");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendArrivedSms(Order order) {
        // 获取运输路线
        Transport transport = transportService.getTransportByTransportNo(order.getTransport().getTransportNo());

        if(transport==null){
            transport = transportService.getTransportByOrderNo(order.getOrderNo());
        }

        TemplateData templateData = new TemplateData();
        templateData.put("endCity", transport.getEndCity());

        // 发件人
        try {
            aliSmsUtils.sendSms(templateData, order.getSenderPhone(), "SMS_192575133");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 收件人
        try {
            aliSmsUtils.sendSms(templateData, order.getReceiverPhone(), "SMS_192575133");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendOutPortMsg(Order order, int transportType) {
        if (TransportTypeEnum.AIRCRAFT.getType() == transportType) {
            sendOutPortAirplaneSms(order);
        } else if (TransportTypeEnum.RAILWAY.getType() == transportType) {
            sendOutPortTrainSms(order);
        } else if (TransportTypeEnum.BUS.getType() == transportType || TransportTypeEnum.SPECIAL_TRAIN.getType() == transportType) {
            sendOutPortBusSms(order);
        }
    }

    @Override
    public void sendOutPortMsgToNextStation(Order order, int transportType,int stationNo) throws Exception{
        Station station = stationService.getStation(stationNo);
        if(station == null){
            return;
        }
        TemplateData data = new TemplateData();
        data.put("name", station.getContact());
        data.put("orderNo",order.getOrderNo());
        aliSmsUtils.sendSms(data, station.getPhone(), "SMS_204276053");
//        TemplateData templateData = newOutPortTemplateData(order);
//        if (TransportTypeEnum.AIRCRAFT.getType() == transportType) {
//            templateData.put("userName", station.getContact());
//            aliSmsUtils.sendSms(templateData, station.getPhone(), "SMS_202020");
//        } else if (TransportTypeEnum.RAILWAY.getType() == transportType) {
//            templateData.put("userName", station.getContact());
//            aliSmsUtils.sendSms(templateData, station.getPhone(), "SMS_202020");
//        } else if (TransportTypeEnum.BUS.getType() == transportType || TransportTypeEnum.SPECIAL_TRAIN.getType() == transportType) {
//            templateData.put("userName", station.getContact());
//            aliSmsUtils.sendSms(templateData, station.getPhone(), "SMS_202020");
//        }
    }

    @Override
    public void sendStationPlaceOrder(Order order) {
        //通过订单获取站点
        Station station = stationService.getByOrderNo(order.getOrderNo());
        // 获取所有的员工列表
        List<Staff> staffList = staffService.listByStationNoAnRole(station.getStationNo(),
                UserRolesEnum.STATION_ADMIN.getUserRoles(),
                UserRolesEnum.CUSTOMER_SERVER.getUserRoles());

        if (CollectionUtils.isNotEmpty(staffList)) {
            TemplateData templateData = new TemplateData();

            // 获取路线信息
            Transport transport = transportService.getTransportByTransportNo(order.getTransport().getTransportNo());

            // 封装数据对象
            templateData.put("startCity", transport.getStartCity());
            templateData.put("endCity", transport.getEndCity());
            templateData.put("orderNo", order.getOrderNo());

            for (Staff staff : staffList) {
                try {
                    aliSmsUtils.sendSms(templateData, staff.getPhone(), "SMS_192370667");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void sendCustomerPlaceOrder(Order order) {
        HashSet<Sender> senderHashSet = new HashSet<>();

        // 发件人以及收件人
        senderHashSet.add(new Sender(order.getReceiverName(), order.getReceiverPhone()));
        senderHashSet.add(new Sender(order.getSenderName(), order.getSenderPhone()));

        // 获取下单人对象
        Customer customer = customerService.getCustomerByCustomerNo(order.getCustomerNo());
        if (customer != null) {
            senderHashSet.add(new Sender(customer.getCustomerName(), customer.getPhone()));
        }

        // 获取运输路线
        Transport transport = transportService.getTransportByTransportNo(order.getTransport().getTransportNo());
        if (transport != null) {
            // 获取站点信息
            Station station = stationService.getByOrderNo(order.getOrderNo());
            if (station != null) {
                TemplateData templateData = new TemplateData();
                templateData.put("startCity", transport.getStartCity());
                templateData.put("endCity", transport.getEndCity());
                templateData.put("stationName", station.getStationName());
                templateData.put("phone", station.getPhone());
                templateData.put("orderNo", order.getOrderNo());

                for (Sender sender : senderHashSet) {
                    templateData.put("customerName", sender.getName());
                    try {
                        aliSmsUtils.sendSms(templateData, sender.getPhone(), "SMS_192370672");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }


    }

    @Override
    public void sendWithdrawNotice(String businessName, String withdrawNo) throws Exception{
        //获取所有管理员
        List<Manager> managers = managerService.listManager();
        if(managers != null && managers.size() > 0){
            for(Manager manager : managers){
                TemplateData result = new TemplateData();
                result.put("name", manager.getManagerName());
                result.put("businessName", businessName);
                aliSmsUtils.sendSms(result, manager.getPhone(), "SMS_205123095");
            }
        }
    }

    /**
     * <p>
     * 创建出港模板消息对象
     * </p>
     *
     * @param order 订单编号
     * @return com.yangwang.sysframework.sms.TemplateData
     * @author LiuXiangLin
     * @date 14:06 2020/5/18
     **/
    private TemplateData newOutPortTemplateData(Order order) {
        TemplateData result = new TemplateData();

        // 获取运输路线
        Transport transport = transportService.getTransportByTransportNo(order.getTransport().getTransportNo());

        // 获取车次号
        OrderTransport orderTransport = orderTransportService.getLastByOrderNo(order.getOrderNo());

        // 获取提货信息
        OrderTakeDetail orderTakeDetail = orderTakeDetailService.getByOrderNo(order.getOrderNo());

        result.put("startCity", TypeConvertUtil.$Str(transport.getStartCity()));
        result.put("endCity", TypeConvertUtil.$Str(transport.getEndCity()));
        result.put("orderNo", TypeConvertUtil.$Str(order.getOrderNo()));
        result.put("transportNum", TypeConvertUtil.$Str(orderTransport.getTransportNum()));
        result.put("dateTime", TypeConvertUtil.$Str(orderTransport.getDateTime()));
        result.put("phone", TypeConvertUtil.$Str(orderTakeDetail!=null?orderTakeDetail.getPhone():null));

        return result;
    }


    class Sender {
        private String name;
        private String phone;

        public Sender(String name, String phone) {
            this.name = name;
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sender sender = (Sender) o;
            return phone.equals(sender.phone);
        }

        @Override
        public int hashCode() {
            return Objects.hash(phone);
        }
    }

}
