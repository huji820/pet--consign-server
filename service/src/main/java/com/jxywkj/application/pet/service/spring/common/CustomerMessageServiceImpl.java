package com.jxywkj.application.pet.service.spring.common;

import com.jxywkj.application.pet.common.enums.MessageTypeEnum;
import com.jxywkj.application.pet.common.utils.CollectionUtils;
import com.jxywkj.application.pet.dao.common.CustomerMessageMapper;
import com.jxywkj.application.pet.model.business.Business;
import com.jxywkj.application.pet.model.business.BusinessWithdraw;
import com.jxywkj.application.pet.model.common.CustomerMessage;
import com.jxywkj.application.pet.model.consign.Order;
import com.jxywkj.application.pet.model.consign.OrderPremium;
import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.service.facade.common.CustomerMessageService;
import com.jxywkj.application.pet.service.facade.common.CustomerService;
import com.jxywkj.application.pet.service.facade.consign.ConsignOrderService;
import com.jxywkj.application.pet.service.facade.consign.OrderAssignmentService;
import com.yangwang.sysframework.utils.DateUtil;
import com.yangwang.sysframework.veriflight.dto.PushFlightResponseData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @ClassName CustomerMessageServiceImpl
 * @Description
 * @Author LiuXiangLin
 * @Date 2019/9/9 15:12
 * @Version 1.0
 **/
@Service
public class CustomerMessageServiceImpl implements CustomerMessageService {

    @Resource
    CustomerMessageMapper customerMessageMapper;

    @Resource
    ConsignOrderService consignOrderService;

    @Resource
    CustomerService customerService;

    @Resource
    OrderAssignmentService orderAssignmentService;

    private static final String WE_APP_ORDER_DETAIL_PAGE = "/pages/orderDetail/orderDetail";


    @Override
    public int countUnreadByPhone(String phone, String lastGetTime) {
        return customerMessageMapper.countByPhone(phone, lastGetTime, MessageTypeEnum.UN_READ.getState());
    }

    @Override
    public List<CustomerMessage> listByPhone(String phone, int offset, int limit) {
        return customerMessageMapper.listByPhone(phone, offset, limit);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveAOrderCustomerMessage(Order order) {
        int result = 0;

        // 获取用户信息
        CustomerMessage customerMessage = new CustomerMessage();
        customerMessage.setMessageTitle("下单成功提示");
        customerMessage.setMessageContent("您的订单已成功生成，单号为：" + order.getOrderNo() +
                "，起始城市为：" + order.getTransport().getStartCity() + "，终点城市为：" + order.getTransport().getEndCity() + "。请耐心等待！");
        customerMessage.setReceiveNo(order.getSenderPhone());
        customerMessage.setSendNo(null);
        customerMessage.setStatus(MessageTypeEnum.UN_READ.getState());
        customerMessage.setSendTime(DateUtil.format(new Date(), DateUtil.FORMAT_FULL));
        customerMessage.setReceiveNo(order.getSenderPhone());

        result += customerMessageMapper.saveAMessage(customerMessage);


        customerMessage.setReceiveNo(order.getReceiverPhone());
        result += customerMessageMapper.saveAMessage(customerMessage);


        return result;

    }

    @Override
    public int confirmOrderCustomerMessage(Order order) {
        int result = 0;
        CustomerMessage customerMessage = new CustomerMessage();
        setBaseCustomerMessage(customerMessage);
        customerMessage.setMessageTitle("订单确认收货提示");
        customerMessage.setMessageContent("您的订单已经确认收货！订单号为：" + order.getOrderNo() + "，感谢您选择淘宠惠，期待下次为您服务。");
        customerMessage.setReceiveNo(order.getSenderPhone());

        result += customerMessageMapper.saveAMessage(customerMessage);

        customerMessage.setReceiveNo(order.getReceiverPhone());
        result += customerMessageMapper.saveAMessage(customerMessage);


        return result;
    }

    @Override
    public int sendOrderAssignmentMessage(List<Integer> staffNoArray) {
        if (!CollectionUtils.isEmpty(staffNoArray)) {
            CustomerMessage customerMessage = new CustomerMessage();
            customerMessage.setMessageTitle("新订单提示");
            customerMessage.setMessageContent("您有分配到了一个新的订单，请及时处理！");
            setBaseCustomerMessage(customerMessage);
            for (int staffNo : staffNoArray) {
                customerMessage.setReceiveNo(String.valueOf(staffNo));
                customerMessageMapper.saveAMessage(customerMessage);
            }
            return staffNoArray.size();
        }

        return 0;
    }

    @Override
    public int sendInPortCustomerMessage(Order order) {
        int result = 0;
        CustomerMessage customerMessage = new CustomerMessage();
        customerMessage.setMessageTitle("入港提示");
        customerMessage.setMessageContent("您的订单已经入港，您可以在待发货中查看订单明细。");
        setBaseCustomerMessage(customerMessage);

        // 发件人
        customerMessage.setReceiveNo(order.getSenderPhone());
        result += customerMessageMapper.saveAMessage(customerMessage);

        // 收件人
        customerMessage.setReceiveNo(order.getReceiverPhone());
        result += customerMessageMapper.saveAMessage(customerMessage);

        return result;
    }

    @Override
    public int sensOutPortCustomerMessage(Order order) {
        int result = 0;
        CustomerMessage customerMessage = new CustomerMessage();
        customerMessage.setMessageTitle("出港提示");
        customerMessage.setMessageContent("您的订单已经出港，您可以在待发货中查看订单明细。");
        setBaseCustomerMessage(customerMessage);


        // 发件人
        customerMessage.setReceiveNo(order.getSenderPhone());
        result += customerMessageMapper.saveAMessage(customerMessage);

        // 收件人
        customerMessage.setReceiveNo(order.getReceiverPhone());
        result += customerMessageMapper.saveAMessage(customerMessage);

        return result;
    }

    @Override
    public int sendArriveCustomerMessage(Order order) {
        int result = 0;

        CustomerMessage customerMessage = new CustomerMessage();
        customerMessage.setMessageTitle("到达提示");
        customerMessage.setMessageContent("您的订单已经达到终点站点。");
        setBaseCustomerMessage(customerMessage);

        // 发件人
        customerMessage.setReceiveNo(order.getSenderPhone());
        result += customerMessageMapper.saveAMessage(customerMessage);

        // 收件人
        customerMessage.setReceiveNo(order.getReceiverPhone());
        result += customerMessageMapper.saveAMessage(customerMessage);

        return result;
    }

    @Override
    public int sendOrderPremiumCustomerMessage(OrderPremium orderPremium) {
        // 获取订单
        Order order = consignOrderService.getConsignOrderByOrderNo(orderPremium.getOrderNo());
        // 创建对象
        CustomerMessage customerMessage = new CustomerMessage();
        customerMessage.setMessageTitle("补价提示");
        customerMessage.setMessageContent("您的订单需要补价，点击此处进行查看。");
        setBaseCustomerMessage(customerMessage);
        customerMessage.setLink(WE_APP_ORDER_DETAIL_PAGE + "?orderno=" + orderPremium.getOrderNo() + "&ablepremium=1");
        customerMessage.setReceiveNo(customerService.getCustomerByCustomerNo(order.getCustomerNo()).getPhone());

        return customerMessageMapper.saveAMessage(customerMessage);
    }

    @Override
    public int saveOrderContactsUpdateMessage(String orderNo) {
        if (orderAssignmentService.countByOrderNo(orderNo) > 0) {
            // 创建站内信对象
            CustomerMessage customerMessage = new CustomerMessage();
            setBaseCustomerMessage(customerMessage);
            customerMessage.setMessageTitle("订单更新提示");
            customerMessage.setMessageContent("订单：" + orderNo + "更新了收件或者发件联系人，请及时处理。点击此处可查看订单明细。");
            customerMessage.setLink(WE_APP_ORDER_DETAIL_PAGE + "?orderno=" + orderNo);
            // 保存站内信
            return customerMessageMapper.saveMessageWithOrderAssigment(customerMessage, orderNo);
        }

        return 0;
    }

    @Override
    public int saveOrderPremiumMessage(OrderPremium orderPremium) {
        // 获取所有的订单的分配人员电话
        if (orderAssignmentService.countByOrderNo(orderPremium.getOrderNo()) > 0) {
            CustomerMessage customerMessage = new CustomerMessage();
            setBaseCustomerMessage(customerMessage);
            customerMessage.setMessageTitle("差价单支付成功提示");
            customerMessage.setMessageContent("订单" + orderPremium.getOrderNo()
                    + "的补价单" + orderPremium.getBillNo()
                    + "已经支付成功！请及时处理。点击此处可以查订单明细。");
            customerMessage.setLink(WE_APP_ORDER_DETAIL_PAGE + "?orderno=" + orderPremium.getOrderNo());

            return customerMessageMapper.saveMessageWithOrderAssigment(customerMessage, orderPremium.getOrderNo());
        }

        return 0;
    }

    @Override
    public int saveStaffAuditPass(Staff staff) {
        CustomerMessage customerMessage = new CustomerMessage();
        setBaseCustomerMessage(customerMessage);
        customerMessage.setMessageTitle("审核通过提示");
        customerMessage.setMessageContent("您申请的员工，审核已经通过。点击此处重新登录。");
        customerMessage.setReceiveNo(staff.getPhone());
        // action是错误操作的意思 login 是具体的操作
        customerMessage.setLink("action?login");

        return customerMessageMapper.saveAMessage(customerMessage);
    }

    @Override
    public int saveBusinessRebate(Order order, Business business) {
        CustomerMessage customerMessage = new CustomerMessage();
        setBaseCustomerMessage(customerMessage);
        customerMessage.setReceiveNo(business.getContactPhone());
        customerMessage.setMessageTitle("返利到账提醒");
        customerMessage.setMessageContent("订单" + order.getOrderNo() + "的返利已经到账，请注意查收。");

        return customerMessageMapper.saveAMessage(customerMessage);
    }

    @Override
    public int saveBusinessWithdrawSuccess(BusinessWithdraw businessWithdraw) {
        CustomerMessage customerMessage = new CustomerMessage();
        setBaseCustomerMessage(customerMessage);
        customerMessage.setMessageTitle("提现到账提醒");
        customerMessage.setMessageContent("您提现的￥" + businessWithdraw.getAmount() + "元已经到账，请注意查收。");

        return customerMessageMapper.saveAMessage(customerMessage);
    }

    @Override
    public int saveBusinessRejectWithdraw(BusinessWithdraw businessWithdraw) {
        CustomerMessage customerMessage = new CustomerMessage();
        setBaseCustomerMessage(customerMessage);
        customerMessage.setMessageTitle("提现驳回提醒");
        customerMessage.setMessageContent("您提现的￥" + businessWithdraw.getAmount() + "元被驳回。");

        return customerMessageMapper.saveAMessage(customerMessage);
    }

    @Override
    public int sendVariflight(Order order, PushFlightResponseData pushFlightResponseData) {
        CustomerMessage customerMessage = new CustomerMessage();
        setBaseCustomerMessage(customerMessage);
        customerMessage.setMessageTitle("航班信息推送");
        customerMessage.setMessageContent("您的宠物搭乘的航班为"
                + pushFlightResponseData.getFlightCompany() +
                pushFlightResponseData.getFlightNo() + "号航班，" +
                "预计起飞时间为" + pushFlightResponseData.getFlightDeptimePlanDate() +
                "，预计到达时间为" + pushFlightResponseData.getFlightArrtimePlanDate() +
                "，当前航班状态为" + pushFlightResponseData.getFlightState());

        // 收件人
        customerMessage.setReceiveNo(order.getReceiverPhone());
        int result = customerMessageMapper.saveAMessage(customerMessage);

        // 发件人
        customerMessage.setReceiveNo(order.getSenderPhone());
        result += customerMessageMapper.saveAMessage(customerMessage);

        return result;
    }

    @Override
    public int saveRejectBusiness(Business business) {
        CustomerMessage customerMessage = new CustomerMessage();
        setBaseCustomerMessage(customerMessage);
        customerMessage.setReceiveNo(business.getContactPhone());
        customerMessage.setMessageTitle("驿站申请驳回提醒");
        customerMessage.setMessageContent("您申请的" + business.getBusinessName() + "驿站被驳回。");

        return customerMessageMapper.saveAMessage(customerMessage);
    }

    @Override
    public int saveRejectStaff(Staff staff) {
        CustomerMessage customerMessage = new CustomerMessage();
        setBaseCustomerMessage(customerMessage);
        customerMessage.setReceiveNo(staff.getPhone());
        customerMessage.setMessageTitle("员工申请驳回提醒");
        customerMessage.setMessageContent("您的站点员工申请已经被驳回。");

        return customerMessageMapper.saveAMessage(customerMessage);
    }

    /**
     * @return void
     * @Author LiuXiangLin
     * @Description 设置消息的基本信息
     * @Date 15:01 2019/9/30
     * @Param [customerMessage]
     **/
    private void setBaseCustomerMessage(CustomerMessage customerMessage) {
        customerMessage.setSendTime(DateUtil.format(new Date(), DateUtil.FORMAT_FULL));
        customerMessage.setUpdateTime(DateUtil.format(new Date(), DateUtil.FORMAT_FULL));
        customerMessage.setSendNo(null);
        customerMessage.setStatus(MessageTypeEnum.UN_READ.getState());
    }
}
