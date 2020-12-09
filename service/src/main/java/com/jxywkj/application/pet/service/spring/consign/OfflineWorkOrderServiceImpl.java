package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.common.enums.BusinessStateEnum;
import com.jxywkj.application.pet.common.enums.OrderStateEnum;
import com.jxywkj.application.pet.common.enums.OrderStatusEnum;
import com.jxywkj.application.pet.common.enums.StaffStateEnum;
import com.jxywkj.application.pet.common.utils.StringUtils;
import com.jxywkj.application.pet.dao.consign.ConsignOrderMapper;
import com.jxywkj.application.pet.dao.consign.OfflineWorkOrderMapper;
import com.jxywkj.application.pet.model.common.PetGenre;
import com.jxywkj.application.pet.model.common.PetSort;
import com.jxywkj.application.pet.model.consign.OfflineOrder;
import com.jxywkj.application.pet.model.consign.OrderState;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.model.consign.Transport;
import com.jxywkj.application.pet.model.consign.params.OfflineWorkOrderDTO;
import com.jxywkj.application.pet.model.consign.params.OrderPrice;
import com.jxywkj.application.pet.service.facade.business.BusinessService;
import com.jxywkj.application.pet.service.facade.consign.*;
import com.jxywkj.application.pet.service.spring.consign.strategy.transport.price.JoinTransportOrderPrice;
import com.jxywkj.application.pet.service.spring.consign.strategy.transport.price.RetailTransportOrderPrice;
import com.yangwang.sysframework.utils.DateUtil;
import com.yangwang.sysframework.utils.PinyinUtil;
import com.yangwang.sysframework.utils.TypeConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName OfflineWorkOrderServiceImpl
 * @Description: 线下生成工单Service实现类
 * @Author GuoPengCheng
 * @Date 2020/7/17 11:56
 * @Version 1.0
 **/
@Service
public class OfflineWorkOrderServiceImpl implements OfflineWorkOrderService{


    @Autowired
    TransportService transportService;

    @Resource
    StaffService staffService;

    @Resource
    BusinessService businessService;

    @Resource
    ConsignOrderLedgerService consignOrderLedgerService;

    @Resource
    OrderStateService orderStateService;

    @Autowired
    StationService stationService;

    @Autowired
    OfflineWorkOrderMapper offlineWorkOrderMapper;

    @Autowired
    ConsignOrderMapper consignOrderMapper;

    @Resource
    JoinTransportOrderPrice joinTransportOrderPrice;

    @Resource
    RetailTransportOrderPrice retailTransportOrderPrice;

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
    @Transactional(rollbackFor = Exception.class)
    public String insertOfflineWorkOrder(OfflineWorkOrderDTO offlineWorkOrderDTO) throws Exception {
        //订单日期
        Date nowDate = new Date();
        String orderDate = DateUtil.format(nowDate, DateUtil.FORMAT_SIMPLE);
        String orderTime = DateUtil.format(nowDate, DateUtil.FORMAT_TIME);

        //开始站点
        Station startStation = null;
        if(offlineWorkOrderDTO.getStartStation() != null){
            startStation = stationService.getStation(offlineWorkOrderDTO.getStartStation().getStationNo());
        }

        if (startStation == null){
            throw new Exception();
        }

        //获取员工,通过员工获取当前站点
//        Staff staff = staffService.getStaffByCustomerNoAndStatus(offlineWorkOrderDTO.getCustomerNo(), (short) 1);
//        Station nowStation = stationService.getStation(staff.getStation().getStationNo());

        Transport transport = null;

        // 获取运输路线
        List<Transport> transports = transportService.listTransportByCondition(new Transport(offlineWorkOrderDTO.getStartCity(), offlineWorkOrderDTO.getEndCity(), String.valueOf(offlineWorkOrderDTO.getTransportType())));
        if(transports != null && transports.size() != 0){
            transport = transports.get(0);
        }

        // 订单单号
        String orderNo = getOrderNo(new Date(),getCityShortName(offlineWorkOrderDTO.getStartCity()));

        // 获取价格
        OrderPrice orderPrice = OrderPrice.newZeroInstance();
        if(offlineWorkOrderDTO.getOrderAmount() != null){
            orderPrice.setOrderAmount(offlineWorkOrderDTO.getOrderAmount());
        }
        if(offlineWorkOrderDTO.getOrderDifferentAmount() != null){
            orderPrice.setOrderDifferentAmount(offlineWorkOrderDTO.getOrderDifferentAmount());
        }
        if(offlineWorkOrderDTO.getJoinOrderAmount() != null){
            orderPrice.setJoinOrderAmount(offlineWorkOrderDTO.getJoinOrderAmount());
        }
        if(offlineWorkOrderDTO.getRetailOrderAmount() != null){
            orderPrice.setRetailOrderAmount(offlineWorkOrderDTO.getRetailOrderAmount());
        }
        // 创建订单
        OfflineOrder order = getOrder(offlineWorkOrderDTO, orderDate, orderTime, transport, orderNo, orderPrice, startStation);

        // 插入订单
        offlineWorkOrderMapper.insertOfflineWorkOrder(order);

        // 添加一条流水（下单）
//        addStateAfterOrder(orderNo, orderDate, orderTime, nowStation);
        addStateAfterOrder(orderNo, orderDate, orderTime, startStation);

        return orderNo;
    }

    /**
     * 获取单据编号,订单编号为年月日-城市首字母-0001
     *
     * @param date
     * @return
     */
    @Override
    public synchronized String getOrderNo(Date date, String cityNameAbbreviation) {
        // 获取最后的订单数
        Integer lastOrderIndex = CITY_ORDER_INDEX.get(cityNameAbbreviation);

        // 为空则表示是服务器重启或者是新的一天的第一次
        if (lastOrderIndex == null) {
            // 通过城市获取最后一个订单号
            String lastOrderNo = consignOrderMapper.getLastOrderNoByTimeAndCity(DateUtil.format(date, DateUtil.FORMAT_SIMPLE) + SPACE + DAY_START_TIME,
                    DateUtil.format(date, DateUtil.FORMAT_SIMPLE) + SPACE + DAY_END_TIME, cityNameAbbreviation);

            if (!StringUtils.isEmpty(lastOrderNo)) {
                // 表示今天有新的订单号 截取订单号最后的数字 就是开始的单号
                String[] orderArray = lastOrderNo.split(cityNameAbbreviation);
                if (orderArray.length > 1) {
                    lastOrderIndex = Integer.valueOf(orderArray[1]);
                } else {
                    lastOrderIndex = 0;
                }
            } else {
                // 今天没有新的单号
                lastOrderIndex = 0;
            }
        }
        // 更新map集合中的数据
        CITY_ORDER_INDEX.put(cityNameAbbreviation, ++lastOrderIndex);
        return DateUtil.format(date, "yyyyMMdd") + cityNameAbbreviation + String.format("%03d", lastOrderIndex);
    }
    @Override
    public String getCityShortName(String cityName) {
        String[] result;

        // 切割字符串
        result = cityName.split(REGEX_CITY_KEY_WORD);

        if (result.length == 0) {
            throw new RuntimeException("获取起始城市失败！");
        }

        return PinyinUtil.cn2py(result[0], PinyinUtil.RET_PINYIN_TYPE_HEADCHAR);
    }

    @Override
    public OrderPrice getOrderPrice(Transport transport, OfflineWorkOrderDTO offlineWorkOrderDTO,Station startStation) throws Exception {
        OrderPrice result = null;

        // 是否是员工
        boolean enjoyDiscount = staffService.getStaffByCustomerNoAndStatus(offlineWorkOrderDTO.getCustomerNo(), StaffStateEnum.NORMAL.getType()) != null;
        // 通过openId获取商家对象
        if (!enjoyDiscount) {
            enjoyDiscount = businessService.getBusinessByPhone(offlineWorkOrderDTO.getCustomerNo(), BusinessStateEnum.NORMAL) != null;
        }

        //获取付款类型 0 客户价  1 合作价 2 自定义付款
        String payAmountType = offlineWorkOrderDTO.getPayAmountType();

        // 获取合作价
        OrderPrice joinPrice = joinTransportOrderPrice.calcOrderPrice(transport, offlineWorkOrderDTO,startStation);

        // 获取客户价格
        OrderPrice retailPrice = retailTransportOrderPrice.calcOrderPrice(transport, offlineWorkOrderDTO,startStation);

        if("0".equals(payAmountType)){  //客户价
            result = retailPrice;
            result.setOrderDifferentAmount(retailPrice.getOrderAmount().subtract(joinPrice.getOrderAmount()));
        }else if("1".equals(payAmountType)){    //合作价
            result = joinPrice;
            result.setOrderDifferentAmount(BigDecimal.ZERO);
        }else if("2".equals(payAmountType)){        //自定义价格
            result = retailPrice;
            result.setOrderAmount(offlineWorkOrderDTO.getCustomPrice());
            result.setOrderDifferentAmount(retailPrice.getOrderAmount().subtract(joinPrice.getOrderAmount()));
        }

        // 如果参与客户价
//        if (enjoyDiscount) {
//            result = joinPrice;
//            result.setOrderDifferentAmount(BigDecimal.ZERO);
//        } else {
//            result = retailPrice;
//            result.setOrderDifferentAmount(retailPrice.getOrderAmount().subtract(joinPrice.getOrderAmount()));
//        }
        result.setRetailOrderAmount(retailPrice.getOrderAmount());
        result.setJoinOrderAmount(joinPrice.getOrderAmount());

        // 格式化数据
        result.setOrderAmount(result.getOrderAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN));
        result.setOrderDifferentAmount(result.getOrderDifferentAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN));

        return result;
    }

    /**
     * 通过工单号和工单标识符获取工单价格
     * @param orderNo
     * @return
     */
    @Override
    public Integer getOfflineWorkOrderPrice(String orderNo) {
        return offlineWorkOrderMapper.getOfflineWorkOrderPrice(orderNo);
    }

    /***
     * @author GuoPengCheng
     * @Description 获取订单对象
     * @Date 16:10 2020/7/21
     * @Param [offlineWorkOrderDTO, orderDate, orderTime, transport, orderNo, finalRetailPrice, finalJoinPrice, petSort, PetGenre, addedAirBox, addedInsure, onDoorReceiptServiceDTO, onDoorSendServiceDTO]
     * @return com.jxywkj.application.pet.model.consign.Order
     **/
    private OfflineOrder getOrder(OfflineWorkOrderDTO offlineWorkOrderDTO, String orderDate, String orderTime, Transport transport, String orderNo, OrderPrice orderPrice,Station station) throws Exception{
        if (StringUtils.isBlank(offlineWorkOrderDTO.getReceiverName()) || StringUtils.isBlank(offlineWorkOrderDTO.getReceiverPhone())) {
            throw new Exception();
        }
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
        order.setState(OrderStatusEnum.RECEIVING.getState());
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
        order.setOutTradeNo(getWeChatOutTradeNo(getCityShortName(offlineWorkOrderDTO.getStartCity())));
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
        orderState.setCurrentPosition(OrderStateEnum.TO_BE_IN_PORT.getDescription());
        orderState.setOrderType(OrderStateEnum.TO_BE_IN_PORT.getType());
        orderState.setStation(startStation);
        orderStateService.saveOrderState(orderState);
    }

    /**
     * @return void
     * @Author GuoPengCheng
     * @Description 下单只有添加一条流水
     * @Date 16:04 2020/7/21
     * @Param [orderNo]
     **/
    private void addStateIsArrivedAfterOrder(String orderNo, String orderDate, String orderTime, Station startStation) {
        OrderState orderState = new OrderState();
        orderState.setOrderNo(orderNo);
        orderState.setSn(0);
        orderState.setDate(orderDate);
        orderState.setTime(orderTime);
        orderState.setCurrentPosition(OrderStateEnum.TO_BE_ARRIVED.getDescription());
        orderState.setOrderType(OrderStateEnum.TO_BE_ARRIVED.getType());
        orderState.setStation(startStation);
        orderStateService.saveOrderState(orderState);
    }
}
