package com.jxywkj.application.pet.service.facade.common;

import com.jxywkj.application.pet.model.business.Business;
import com.jxywkj.application.pet.model.business.BusinessWithdraw;
import com.jxywkj.application.pet.model.common.CustomerMessage;
import com.jxywkj.application.pet.model.consign.Order;
import com.jxywkj.application.pet.model.consign.OrderPremium;
import com.jxywkj.application.pet.model.consign.Staff;
import com.yangwang.sysframework.veriflight.dto.PushFlightResponseData;

import java.util.List;

/**
 * @ClassName CustomerMessageService
 * @Description 站内消息(用户以及员工)
 * @Author LiuXiangLin
 * @Date 2019/9/9 15:09
 * @Version 1.0
 **/
public interface CustomerMessageService {
    /**
     * @param [phone, lastGetTime]
     * @return int
     * @author LiuXiangLin
     * @description 通过电话号码和最后获取时间查询条数（员工和用户）
     * @date 15:23 2019/10/14
     **/
    int countUnreadByPhone(String phone, String lastGetTime);

    /**
     * @param phone , offset , limit
     * @return java.util.List<com.jxywkj.application.pet.model.common.CustomerMessage>
     * @author LiuXiangLin
     * @description 通过电话号码查询数据
     * @date 15:25 2019/10/14
     **/
    List<CustomerMessage> listByPhone(String phone, int offset, int limit);

    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 添加一条下单消息提示
     * @Date 15:56 2019/9/10
     * @Param [order]
     **/
    int saveAOrderCustomerMessage(Order order);

    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 确认订单站内信
     * @Date 16:20 2019/9/10
     * @Param [orderNo]
     **/
    int confirmOrderCustomerMessage(Order order);

    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 发送订单分配的站内信息
     * @Date 15:25 2019/9/24
     * @Param [staffNoArray]
     **/
    int sendOrderAssignmentMessage(List<Integer> staffNoArray);

    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 发送入港客户站内信
     * @Date 14:58 2019/9/30
     * @Param [order]
     **/
    int sendInPortCustomerMessage(Order order);


    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 发送出港站内信
     * @Date 15:19 2019/9/30
     * @Param [order]
     **/
    int sensOutPortCustomerMessage(Order order);

    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 发送到达客户站内信
     * @Date 15:46 2019/9/30
     * @Param [order]
     **/
    int sendArriveCustomerMessage(Order order);

    /**
     * @param [orderPremium]
     * @return int
     * @author LiuXiangLin
     * @description 添加一条补价单站内信
     * @date 18:14 2019/10/11
     **/
    int sendOrderPremiumCustomerMessage(OrderPremium orderPremium);

    /**
     * @param [orderNo]
     * @return int
     * @author LiuXiangLin
     * @description 更新订单信息发送员工站内信
     * @date 9:32 2019/10/14
     **/
    int saveOrderContactsUpdateMessage(String orderNo);

    /**
     * @param [orderPremium]
     * @return int
     * @author LiuXiangLin
     * @description 订单补价站内信所有关联员工发送信息
     * @date 16:17 2019/10/14
     **/
    int saveOrderPremiumMessage(OrderPremium orderPremium);


    /**
     * 发送员工申请审核通过的站内信
     *
     * @param staff 员工对象
     * @return int
     * @author LiuXiangLin
     * @date 17:44 2019/10/28
     **/
    int saveStaffAuditPass(Staff staff);

    /**
     * <p>
     * 订单返利提醒
     * </p>
     *
     * @param order    订单对象
     * @param business 驿站对象
     * @return int
     * @author LiuXiangLin
     * @date 10:23 2019/12/9
     **/
    int saveBusinessRebate(Order order, Business business);

    /**
     * <p>
     * 保存一条驿站提现成功提醒
     * </p>
     *
     * @param businessWithdraw 提现对象
     * @return int
     * @author LiuXiangLin
     * @date 10:36 2019/12/9
     **/
    int saveBusinessWithdrawSuccess(BusinessWithdraw businessWithdraw);

    /**
     * <p>
     * 保存一条驿站提心驳回提示
     * </p>
     *
     * @param businessWithdraw 提现对象
     * @return int
     * @author LiuXiangLin
     * @date 10:37 2019/12/9
     **/
    int saveBusinessRejectWithdraw(BusinessWithdraw businessWithdraw);

    /**
     * <p>
     * 发送非常准站内信推送
     * </p>
     *
     * @param order                  订单对象
     * @param pushFlightResponseData 非常准系信息推送对象
     * @return int
     * @author LiuXiangLin
     * @date 13:56 2019/12/11
     **/
    int sendVariflight(Order order, PushFlightResponseData pushFlightResponseData);

    /**
     * <p>
     * 驳回申请消息推送
     * </p>
     *
     * @param business 商家
     * @return int
     * @author LiuXiangLin
     * @date 14:37 2020/1/10
     **/
    int saveRejectBusiness(Business business);

    /**
     * <p>
     * 发送驳回员工申请的消息推送
     * </p>
     *
     * @param staff 员工对象
     * @return int
     * @author LiuXiangLin
     * @date 14:45 2020/1/10
     **/
    int saveRejectStaff(Staff staff);
}
