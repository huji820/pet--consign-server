package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.common.enums.OrderStateEnum;
import com.jxywkj.application.pet.common.enums.OrderStatusEnum;
import com.jxywkj.application.pet.common.utils.StringUtils;
import com.jxywkj.application.pet.dao.consign.ConsignOrderMapper;
import com.jxywkj.application.pet.dao.consign.OfflineWorkOrderMapper;
import com.jxywkj.application.pet.model.common.PetGenre;
import com.jxywkj.application.pet.model.common.PetSort;
import com.jxywkj.application.pet.model.consign.*;
import com.jxywkj.application.pet.model.consign.params.OfflineWorkOrderDTO;
import com.jxywkj.application.pet.model.consign.params.OrderPrice;
import com.jxywkj.application.pet.service.facade.business.BusinessService;
import com.jxywkj.application.pet.service.facade.consign.*;
import com.yangwang.sysframework.utils.DateUtil;
import com.yangwang.sysframework.utils.TypeConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName GoingAbroadService
 * @Description: 出国板块Service实现类
 * @Date 2020/7/23 11:01
 * @Version 1.0
 **/
@Service
@Deprecated
public class GoingAbroadServiceImpl implements GoingAbroadService{


    @Autowired
    TransportService transportService;

    @Resource
    StaffService staffService;

    @Resource
    BusinessService businessService;

    @Resource
    ConsignOrderLedgerService consignOrderLedgerService;

    @Resource
    OfflineWorkOrderService offlineWorkOrderService;

    @Resource
    OfflineWorkOrderMapper offlineWorkOrderMapper;

    @Resource
    OrderStateService orderStateService;

    @Autowired
    StationService stationService;

    @Autowired
    ConsignOrderMapper consignOrderMapper;

    /**
     * 截取市名称的关键字
     */
    private static final String REGEX_CITY_KEY_WORD = "市";
    /**
     * 订单角标
     */
    public static final Map<String, Integer> CITY_ORDER_INDEX = new ConcurrentHashMap<>();
    /**
     * 一天的开始时间
     */
    private static final String DAY_START_TIME = "00:00:00";
    /**
     * 一天的结束时间
     */
    private static final String DAY_END_TIME = "23:59:59";
    /**
     * 空格内容
     */
    private static final String SPACE = " ";
    /**
     * 商家单号格式化时间
     */
    private static final String OUT_TRADE_NO_FORMAT = "yyyyMMddHHmmss";

    /**
     *工单标识符
     */
    private static final String OFFLINE_WORK_ORDER_NO = "1";

    @Override
    public String insertOrder(OfflineWorkOrderDTO offlineWorkOrderDTO) throws Exception {
        //订单日期
        Date nowDate = new Date();
        String orderDate = DateUtil.format(nowDate, DateUtil.FORMAT_SIMPLE);
        String orderTime = DateUtil.format(nowDate, DateUtil.FORMAT_TIME);

        Transport transport = new Transport(offlineWorkOrderDTO.getStartCity(), offlineWorkOrderDTO.getEndCity(), String.valueOf(offlineWorkOrderDTO.getTransportType()));
        // 获取运输路线
        List<Transport> transports = transportService.listTransportByCondition(new Transport(offlineWorkOrderDTO.getStartCity(), offlineWorkOrderDTO.getEndCity(), String.valueOf(offlineWorkOrderDTO.getTransportType())));
        if(transports!=null&&transports.size()!=0){
            transport = transports.get(0);
        }

        // 员工线下生成工单默认起始站点为员工所在站点
        //获取员工,通过员工获取站点
        Staff staff = staffService.getStaffByCustomerNoAndStatus(offlineWorkOrderDTO.getCustomerNo(), (short) 1);
        Station startStation = stationService.getStation(staff.getStation().getStationNo());
        // 订单单号
        String orderNo = offlineWorkOrderService.getOrderNo(new Date(),
                offlineWorkOrderService.getCityShortName(offlineWorkOrderDTO.getStartCity()));

        // 获取价格
        OrderPrice orderPrice = null;
        if(transport.getTransportNo()==0){    //运输路线No为0初始化价格为0
            orderPrice = OrderPrice.newZeroInstance();
        }else{
            orderPrice = offlineWorkOrderService.getOrderPrice(transport,offlineWorkOrderDTO,startStation);
        }

        // 创建订单
        OfflineOrder order = getOrder(offlineWorkOrderDTO, orderDate, orderTime, transport, orderNo, orderPrice,startStation);

        // 插入订单
        offlineWorkOrderMapper.insertOfflineWorkOrder(order);

        // 添加一条流水（下单）
        addStateAfterOrder(orderNo, orderDate, orderTime, startStation);

        // 添加一条站点流水
        consignOrderLedgerService.insertOrderLedger(new OrderLedger(order,startStation));

        return orderNo;
    }

    /***
     * @author GuoPengCheng
     * @Description 获取订单对象
     * @Date 16:10 2020/7/21
     * @Param [offlineWorkOrderDTO, orderDate, orderTime, transport, orderNo, finalRetailPrice, finalJoinPrice, petSort, PetGenre, addedAirBox, addedInsure, onDoorReceiptServiceDTO, onDoorSendServiceDTO]
     * @return com.jxywkj.application.pet.model.consign.Order
     **/
    private OfflineOrder getOrder(OfflineWorkOrderDTO offlineWorkOrderDTO, String orderDate, String orderTime, Transport transport, String orderNo, OrderPrice orderPrice,Station station) {
//        Order order = new Order();
        OfflineOrder order = new OfflineOrder();
        // 订单单号
        order.setOrderNo(orderNo);
        // 运输路线
        order.setTransport(transport);
        // 出发日期
        order.setLeaveDate(offlineWorkOrderDTO.getLeaveDate());
        // 下单日期
        order.setOrderDate(orderDate);
        // 下单时间
        order.setOrderTime(orderTime);
        // 宠物类型
        order.setPetSort(new PetSort(offlineWorkOrderDTO.getPetType()));
        // 宠物类别
        order.setPetGenre(new PetGenre(offlineWorkOrderDTO.getPetClassify()));
        // 宠物重量
        order.setWeight(offlineWorkOrderDTO.getWeight());
        // 数量
        order.setNum(offlineWorkOrderDTO.getNum());
        // 成交价格
        order.setFinalRetailPrice(orderPrice.getOrderAmount());
        // 商家底价
        order.setFinalJoinPrice(orderPrice.getOrderAmount().subtract(orderPrice.getOrderDifferentAmount()));
        // 最终付款金额
        order.setPaymentAmount(orderPrice.getOrderAmount());
        // 航空箱
        order.setAddedWeightCage(orderPrice.getAddedWeightCage());
        // 宠物重量
        order.setPetAmount(offlineWorkOrderDTO.getPetAmount());
        // 保价
        order.setAddedInsure(orderPrice.getAddedInsure());
        // 寄件人姓名
        order.setSenderName(offlineWorkOrderDTO.getSenderName());
        // 寄件人电话
        order.setSenderPhone(offlineWorkOrderDTO.getSenderPhone());
        // 送宠上门地址
        order.setSendAddress(offlineWorkOrderDTO.getSendAddress());
        // 送宠上门经度
        order.setSendLongitude(orderPrice.getSendAmount() == null || orderPrice.getSendAmount().getLnt() == null ? null : String.valueOf(orderPrice.getSendAmount().getLnt()));
        // 送宠上门纬度
        order.setSendLatitude(orderPrice.getSendAmount() == null || orderPrice.getSendAmount().getLat() == null ? null : String.valueOf(orderPrice.getSendAmount().getLat()));
        // 送宠上门价格
        order.setSendAmount(orderPrice.getSendAmount() == null ? BigDecimal.ZERO : orderPrice.getSendAmount().getAmount());
        // 收件人姓名
        order.setReceiverName(offlineWorkOrderDTO.getReceiverName());
        // 收件人电话
        order.setReceiverPhone(offlineWorkOrderDTO.getReceiverPhone());
        // 上门接送地址
        order.setReceiptAddress(offlineWorkOrderDTO.getReceiptAddress());
        // 上门接送经度
        order.setReceiptLongitude(orderPrice.getReceiptAmount() == null || orderPrice.getReceiptAmount().getLnt() == null ? null : String.valueOf(orderPrice.getReceiptAmount().getLnt()));
        // 上门接宠纬度
        order.setReceiptLatitude(orderPrice.getReceiptAmount() == null || orderPrice.getReceiptAmount().getLat() == null ? null : String.valueOf(orderPrice.getReceiptAmount().getLat()));
        // 上门接宠金额
        order.setReceiptAmount(orderPrice.getReceiptAmount() == null || orderPrice.getReceiptAmount().getAmount() == null ? null : orderPrice.getReceiptAmount().getAmount());
        // 状态
        order.setState(OrderStatusEnum.TO_BE_PAID.getState());
        // 备注
        order.setRemarks(offlineWorkOrderDTO.getRemarks());
        // 是否需要食物
        order.setGiveFood(TypeConvertUtil.$int(offlineWorkOrderDTO.getGiveFood()) == 0 ? "否" : "是");
        // 是否中介担保
        order.setGuarantee(TypeConvertUtil.$int(offlineWorkOrderDTO.getGuarantee()) == 0 ? "否" : "是");
        // 用户编号
        order.setCustomerNo(offlineWorkOrderDTO.getCustomerNo());
        // 分享人openId
        order.setShareOpenId(offlineWorkOrderDTO.getShareOpenId());
        // 宠物年龄
        order.setPetAge(offlineWorkOrderDTO.getPetAge());
        // 微信支付订单号
        order.setOutTradeNo(getWeChatOutTradeNo(offlineWorkOrderService.getCityShortName(offlineWorkOrderDTO.getStartCity())));
        //工单标识
        order.setOfflineWorkOrderNo(OFFLINE_WORK_ORDER_NO);
        //所属站点
        order.setStation(station);
        return order;
    }
    /**
     * <p>
     * 获取微信支付商户单号
     * </p>
     *
     * @param cityPy 城市首字子母
     * @return java.lang.String
     * @author LiuXiangLin
     * @date 10:37 2019/11/25
     **/
    private synchronized String getWeChatOutTradeNo(String cityPy) {
        /*生成规则：城市每名称*/
        String nowTime = DateUtil.format(new Date(), OUT_TRADE_NO_FORMAT);
        String uuid = StringUtils.getUuid();
        return cityPy + nowTime + uuid.substring(uuid.length() - 4);
    }
    /**
     * @return void
     * @Author GuoPengCheng
     * @Description 下单只有添加一条流水
     * @Date 16:04 2020/7/21
     * @Param [orderNo]
     **/
    private void addStateAfterOrder(String orderNo, String orderDate, String orderTime, Station startStation) {
        OrderState orderState = new OrderState();
        orderState.setOrderNo(orderNo);
        orderState.setSn(0);
        orderState.setDate(orderDate);
        orderState.setTime(orderTime);
        orderState.setCurrentPosition(OrderStateEnum.TO_BE_PAID.getDescription());
        orderState.setOrderType(OrderStateEnum.TO_BE_PAID.getType());
        orderState.setStation(startStation);
        orderStateService.saveOrderState(orderState);
    }

}
