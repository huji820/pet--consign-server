package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.common.enums.*;
import com.jxywkj.application.pet.common.utils.CollectionUtils;
import com.jxywkj.application.pet.common.utils.StringUtils;
import com.jxywkj.application.pet.dao.consign.ConsignOrderMapper;
import com.jxywkj.application.pet.model.common.*;
import com.jxywkj.application.pet.model.consign.*;
import com.jxywkj.application.pet.model.consign.dto.OrderUpdateDTO;
import com.jxywkj.application.pet.model.consign.params.OrderDTO;
import com.jxywkj.application.pet.model.consign.params.OrderPrice;
import com.jxywkj.application.pet.model.consign.params.StaffOrderQueryDTO;
import com.jxywkj.application.pet.model.consign.vo.OrderVO;
import com.jxywkj.application.pet.model.dto.OrderQueryDto;
import com.jxywkj.application.pet.service.facade.business.BusinessService;
import com.jxywkj.application.pet.service.facade.common.CustomerMessageService;
import com.jxywkj.application.pet.service.facade.common.CustomerOpenIdService;
import com.jxywkj.application.pet.service.facade.common.CustomerService;
import com.jxywkj.application.pet.service.facade.common.SmsSendService;
import com.jxywkj.application.pet.service.facade.consign.*;
import com.jxywkj.application.pet.service.spring.consign.strategy.transport.price.JoinTransportOrderPrice;
import com.jxywkj.application.pet.service.spring.consign.strategy.transport.price.RetailTransportOrderPrice;
import com.yangwang.sysframework.utils.DateUtil;
import com.yangwang.sysframework.utils.JsonUtil;
import com.yangwang.sysframework.utils.PinyinUtil;
import com.yangwang.sysframework.utils.TypeConvertUtil;
import com.yangwang.sysframework.utils.network.HttpUtil;
import com.yangwang.sysframework.wechat.pay.WXPay;
import com.yangwang.sysframework.wechat.pay.WxPayBody;
import com.yangwang.sysframework.wechat.pay.WxPayType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName ConsignOrderServiceImpl
 * @Description: 托运订单Service实现类
 * @Author Aze
 * @Date 2019/7/13 10:06
 * @Version 1.0
 **/
@Service
public class ConsignOrderServiceImpl implements ConsignOrderService {

    /**
     * 截取市名称的关键字
     */
    private static final String REGEX_CITY_KEY_WORD = "市";

    /**
     * 商家单号格式化时间
     */
    private static final String OUT_TRADE_NO_FORMAT = "yyyyMMddHHmmss";

    private static final String FILE_PICTURE = "图片";

    private static final String FILE_VIDEO = "视频";

    @Autowired
    ConsignOrderMapper consignOrderMapper;

    @Resource
    StaffService staffService;

    @Resource
    SmsSendService smsSendService;

    @Resource
    ConsignOrderPriceService consignOrderPriceService;

    @Autowired
    HttpUtil httpUtil;

    @Autowired
    TransportService transportService;

    @Autowired
    StationService stationService;

    @Autowired
    WXPay wxPay;

    @Resource
    OrderCallBackService orderCallBackService;

    @Resource
    CustomerOpenIdService customerOpenIdService;

    @Value("${localHost}")
    String localHost;

    @Value("${pictureHost}")
    String pictureHost;

    @Resource
    ConsignOrderService consignOrderService;

    @Resource
    OrderStateService orderStateService;

    @Resource
    CustomerMessageService customerMessageService;

    @Resource
    StationMessageService stationMessageService;

    @Resource
    BusinessService businessService;

    @Resource
    ConsignOrderRefundService consignOrderRefundService;

    @Resource
    StationBalanceService stationBalanceService;

    @Resource
    ConsignOrderLedgerService consignOrderLedgerService;

    @Resource
    CustomerService customerService;

    @Resource
    JoinTransportOrderPrice joinTransportOrderPrice;

    @Resource
    RetailTransportOrderPrice retailTransportOrderPrice;

    @Resource
    OrderAssignmentService assignmentService;
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
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @Author LiuXiangLin
     * @Description 新增订单
     * @Date 15:14 2019/7/17
     * @Param [orderDTO]
     **/
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String insertConsignOrder(OrderDTO orderDTO) throws Exception {
        // 订单日期
        Date nowDate = new Date();
        String orderDate = DateUtil.format(nowDate, DateUtil.FORMAT_SIMPLE);
        String orderTime = DateUtil.format(nowDate, DateUtil.FORMAT_TIME);

        // 获取运输路线
        Transport transport = transportService.listTransportByCondition(new Transport(orderDTO.getStartCity(), orderDTO.getEndCity(), String.valueOf(orderDTO.getTransportType()))).get(0);

        Station startStation;
        // 是否是员工
        Staff staff = staffService.getStaffByCustomerNoAndStatus(orderDTO.getCustomerNo(), StaffStateEnum.NORMAL.getType());

        //客户选择了站点
        if(orderDTO.getStartStationNo() != null&&(!orderDTO.getStartStationNo().equals(""))&&(!orderDTO.getStartStationNo().equals("null"))){
            //起始站点为客户所选择的站点
            startStation = stationService.getStation(TypeConvertUtil.$int(orderDTO.getStartStationNo()));
        }else if(staff != null){
            //员工下单订单属于员工所在站点
            startStation = stationService.getStation(staff.getStation().getStationNo());
            if(!(startStation.getCity().equals(orderDTO.getStartCity()))){
                //员工所在站点不属于起点城市的站点，重新分配站点
                List<Station> stations = stationService.listStation(orderDTO.getStartCity());
                int index = (int)Math.random()*(stations.size()-1);
                startStation = stations.get(index);
            }
        }else{
            // 客户下单订单随机分配给起始城市的站点
            List<Station> stations = stationService.listStation(orderDTO.getStartCity());
            int index = (int)Math.random()*(stations.size()-1);
            startStation = stations.get(index);
        }

        // 订单单号
        String orderNo = getOrderNo(new Date(), getCityShortName(orderDTO.getStartCity()));

        // 获取价格
        OrderPrice orderPrice = getOrderPrice(transport, orderDTO,startStation);

        // 创建订单
        Order order = getOrder(orderDTO, orderDate, orderTime, transport, orderNo, orderPrice,startStation);

        if(staff != null){  //员工下单，当前订单归属于该员工
            order.setStaff(staff);
        }
        // 插入订单
        consignOrderMapper.insertConsignOrder(order);

        // 添加一条流水（下单）
        addStateAfterOrder(orderNo, orderDate, orderTime, startStation);

        // 添加一条帐也流水
        consignOrderLedgerService.insertOrderLedger(new OrderLedger(order,startStation));

        return orderNo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String insertConsignLedgerOrder(OrderLedger orderLedger, Transport transport) {
        Date now = new Date();
        OrderDTO orderDTO = new OrderDTO(orderLedger, transport);

        // 创建订单
        Order order = getOrder(orderDTO, orderLedger.getOrderDate(), orderLedger.getOrderTime(), transport, orderLedger.getOrderNo(), new OrderPrice(BigDecimal.ZERO, BigDecimal.ZERO, null, null),orderLedger.getStation());

        // 获取用户
        Customer customer = customerService.getCustomerByPhoneNumber(order.getSenderPhone());
        if (customer != null) {
            order.setCustomerNo(customer.getCustomerNo());
        }

        // 插入订单
        consignOrderMapper.insertConsignOrder(order);

        // 回写单据同步成功
        consignOrderLedgerService.updateOrderLedgerSync(orderLedger.getStation().getStationNo(), orderLedger.getOrderNo());

        //获取当前站点
        //下一站点,默认为终点站点
        List<Station> outStations = stationService.listStation(orderDTO.getStartCity());

        String currentPosition = "";

        if (outStations != null) {
            // chugang
            currentPosition = "待出港";
            // 添加一条已经入港的操作
            OrderState newOrderState = new OrderState();
            newOrderState.setOrderNo(orderLedger.getOrderNo());
            newOrderState.setSn(1);
            newOrderState.setDate(DateUtil.format(now, DateUtil.FORMAT_SIMPLE));
            newOrderState.setTime(DateUtil.format(now, DateUtil.FORMAT_TIME));
            newOrderState.setStation(outStations.get(0));
            newOrderState.setOrderType(OrderStateEnum.TO_BE_OUT_PORT.getType());
            newOrderState.setCurrentPosition(currentPosition);
            orderStateService.saveOrderState(newOrderState);
        }

        orderStateService.addInPortState(null, OrderStateEnum.TO_BE_OUT_PORT.getType(), order.getOrderNo(), 1);

        return orderLedger.getOrderNo();
    }

    /**
     * @return void
     * @Author LiuXiangLin
     * @Description 下单只有添加一条流水
     * @Date 11:27 2019/8/9
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


    /***
     * @author LiuXiangLin
     * @Description 获取订单对象
     * @Date 16:20 2019/7/26
     * @Param [orderDTO, orderDate, orderTime, transport, orderNo, finalRetailPrice, finalJoinPrice, petSort, PetGenre, addedAirBox, addedInsure, onDoorReceiptServiceDTO, onDoorSendServiceDTO]
     * @return com.jxywkj.application.pet.model.consign.Order
     **/
    private Order getOrder(OrderDTO orderDTO, String orderDate, String orderTime, Transport transport, String orderNo, OrderPrice orderPrice,Station station) {
        Order order = new Order();
        // 订单单号
        order.setOrderNo(orderNo);
        // 运输路线
        order.setTransport(transport);
        // 出发日期
        order.setLeaveDate(orderDTO.getLeaveDate());
        // 下单日期
        order.setOrderDate(orderDate);
        // 下单时间
        order.setOrderTime(orderTime);
        // 宠物类型
        order.setPetSort(new PetSort(orderDTO.getPetType()));
        // 宠物类别
        order.setPetGenre(new PetGenre(orderDTO.getPetClassify()));
        // 宠物重量
        order.setWeight(orderDTO.getWeight());
        // 数量
        order.setNum(orderDTO.getNum());
        // 成交价格
        order.setFinalRetailPrice(orderPrice.getOrderAmount());
        // 商家底价
        order.setFinalJoinPrice(orderPrice.getOrderAmount().subtract(orderPrice.getOrderDifferentAmount()));
        // 最终付款金额
        order.setPaymentAmount(orderPrice.getOrderAmount());
        // 航空箱
        order.setAddedWeightCage(orderPrice.getAddedWeightCage());
        // 宠物重量
        order.setPetAmount(orderDTO.getPetAmount());
        // 保价
        order.setAddedInsure(orderPrice.getAddedInsure());
        // 寄件人姓名
        order.setSenderName(orderDTO.getSenderName());
        // 寄件人电话
        order.setSenderPhone(orderDTO.getSenderPhone());
        // 送宠上门地址
        order.setSendAddress(orderDTO.getSendAddress());
        // 送宠上门经度
        order.setSendLongitude(orderPrice.getSendAmount() == null || orderPrice.getSendAmount().getLnt() == null ? null : String.valueOf(orderPrice.getSendAmount().getLnt()));
        // 送宠上门纬度
        order.setSendLatitude(orderPrice.getSendAmount() == null || orderPrice.getSendAmount().getLat() == null ? null : String.valueOf(orderPrice.getSendAmount().getLat()));
        // 送宠上门价格
        order.setSendAmount(orderPrice.getSendAmount() == null ? BigDecimal.ZERO : orderPrice.getSendAmount().getAmount());
        // 收件人姓名
        order.setReceiverName(orderDTO.getReceiverName());
        // 收件人电话
        order.setReceiverPhone(orderDTO.getReceiverPhone());
        // 上门接送地址
        order.setReceiptAddress(orderDTO.getReceiptAddress());
        // 上门接送经度
        order.setReceiptLongitude(orderPrice.getReceiptAmount() == null || orderPrice.getReceiptAmount().getLnt() == null ? null : String.valueOf(orderPrice.getReceiptAmount().getLnt()));
        // 上门接宠纬度
        order.setReceiptLatitude(orderPrice.getReceiptAmount() == null || orderPrice.getReceiptAmount().getLat() == null ? null : String.valueOf(orderPrice.getReceiptAmount().getLat()));
        // 上门接宠金额
        order.setReceiptAmount(orderPrice.getReceiptAmount() == null || orderPrice.getReceiptAmount().getAmount() == null ? null : orderPrice.getReceiptAmount().getAmount());
        // 状态
        order.setState(OrderStatusEnum.TO_BE_PAID.getState());
        // 备注
        order.setRemarks(orderDTO.getRemarks());
        // 是否需要猫猫套餐
        order.setGiveFood(TypeConvertUtil.$int(orderDTO.getGiveFood()) == 0 ? "否" : "是");
        // 是否中介担保
        order.setGuarantee(TypeConvertUtil.$int(orderDTO.getGuarantee()) == 0 ? "否" : "是");
        // 用户编号
        order.setCustomerNo(orderDTO.getCustomerNo());
        // 分享人openId
        order.setShareOpenId(orderDTO.getShareOpenId());
        // 宠物年龄
        order.setPetAge(orderDTO.getPetAge());
        // 微信支付订单号
        order.setOutTradeNo(getWeChatOutTradeNo(getCityShortName(orderDTO.getStartCity())));
        //推荐人姓名
        order.setRecommendName(orderDTO.getRecommendName());
        //推荐人手机号
        order.setRecommendPhone(orderDTO.getRecommendPhone());
        //推荐人备注信息
        order.setRecommendRemarks(orderDTO.getRecommendRemarks());
        //支付价格类型
        order.setPayAmountType(orderDTO.getPayAmountType());
        //所属站点
        order.setStation(station);
        return order;
    }

    /**
     * @return
     * @Author LiuXiangLin
     * @Description 获取城市名称的缩写
     * @Date 14:08 2019/7/18
     * @Param
     **/
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
    public OrderPrice getOrderPrice(Transport transport, OrderDTO orderDTO,Station station) throws Exception {
        OrderPrice result;

        // 是否是员工
        boolean enjoyDiscount = staffService.getStaffByCustomerNoAndStatus(orderDTO.getCustomerNo(), StaffStateEnum.NORMAL.getType()) != null;
        // 通过openId获取商家对象
        if (!enjoyDiscount) {
            enjoyDiscount = businessService.getBusinessByPhone(orderDTO.getCustomerNo(), BusinessStateEnum.NORMAL) != null;
        }

        if(orderDTO.getPetAmount().compareTo(new BigDecimal(1000))<0){
            orderDTO.setPetAmount(new BigDecimal(1000));
        }
        if(orderDTO.getPetAmount().compareTo(new BigDecimal(20000))>0){
            orderDTO.setPetAmount(new BigDecimal(20000));
        }

        // 获取合作价
        OrderPrice joinPrice = joinTransportOrderPrice.calcOrderPrice(transport, orderDTO,station);

        // 获取客户价格
        OrderPrice retailPrice = retailTransportOrderPrice.calcOrderPrice(transport, orderDTO,station);

        // 如果参与客户价
        if (enjoyDiscount) {
            result = joinPrice;
            result.setOrderDifferentAmount(BigDecimal.ZERO);
        } else {
            result = retailPrice;
            result.setOrderDifferentAmount(retailPrice.getOrderAmount().subtract(joinPrice.getOrderAmount()));
        }

        //获取付款类型  0 客户价  1 合作价 2 自定义付款
        String payAmountType = orderDTO.getPayAmountType();

        if("0".equals(payAmountType)){  //客户价
            result = retailPrice;
            result.setOrderDifferentAmount(retailPrice.getOrderAmount().subtract(joinPrice.getOrderAmount()));
        } else if("1".equals(payAmountType)){    //合作价
            result = joinPrice;
            result.setOrderDifferentAmount(BigDecimal.ZERO);
        } else if("2".equals(payAmountType)){        //自定义价格
            result = retailPrice;
            result.setOrderAmount(orderDTO.getCustomPrice());
            result.setOrderDifferentAmount(retailPrice.getOrderAmount().subtract(joinPrice.getOrderAmount()));
        }

        result.setRetailOrderAmount(retailPrice.getOrderAmount());
        result.setJoinOrderAmount(joinPrice.getOrderAmount());

        // 格式化数据
        result.setOrderAmount(result.getOrderAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN));
        result.setOrderDifferentAmount(result.getOrderDifferentAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN));

        return result;
    }

    @Override
    public String getLastOrderNoByTimeAndCity(String startTime, String endTime, String city) {
        return consignOrderMapper.getLastOrderNoByTimeAndCity(startTime, endTime, city);
    }

    @Override
    public void updateConsignOrder(String transportNo, String leaveDate, String orderDate,
                                   String orderTime, String petTypeNo, String petClassifyNo,
                                   String weight, String num) {
    }

    @Override
    public int updateOrder2Paid(String orderNo) {
        return consignOrderMapper.updateOrderState(orderNo, OrderStatusEnum.PAID.getState());
    }

    @Override
    public int updateOrderPrice(String orderNo, BigDecimal beforeAmount, BigDecimal afterAmount) {
        return consignOrderMapper.updateOrderPrice(orderNo, beforeAmount, afterAmount, OrderStatusEnum.TO_BE_PAID.getState());
    }

    @Override
    public List<Order> listOrderState(String state, String customerNo) {
        List<Order> orderList = consignOrderMapper.listOrderByParamType(customerNo, state);
        if(CollectionUtils.isNotEmpty(orderList)){
            for(Order order:orderList){
                //如果没有运输路线，手动添加一个
                if(order.getTransport()==null||order.getTransport().getTransportNo()==-1){
                    Transport transport = transportService.getTransportByOrderNo(order.getOrderNo());
                    order.setTransport(transport);
                }
            }
        }
        return orderList;
    }

    @Override
    public List<Order> listOrdersByStationNo(int stationNo) {
        return consignOrderMapper.listOrderListByStationNo(stationNo);
    }

    @Override
    public Order getOrderDetailWidthState(String orderNo, String customerNo) {
        // 获取订单
        Order order = consignOrderMapper.getOrderDetailWidthState(orderNo, null, staffService.getStaffByCustomerNoAndStatus(customerNo, StaffStateEnum.NORMAL.getType()));
        if (order != null) {
            // 分离列表
            separateMedia(order);
            // 进行排序
            Collections.sort(order.getOrderStates());
            // 查询已分配的员工
            order.setOrderAssignments(assignmentService.listByOrderNo(order.getOrderNo()));
        }
        return order;
    }

    /**
     * @return void
     * @Author LiuXiangLin
     * @Description 分离视频和图片
     * @Date 10:10 2019/8/16
     * @Param [order]
     **/
    public void separateMedia(Order order) {
        // 图片列表
        List<OrderMedia> pictures;
        // 视频列表
        List<OrderMedia> videos;
        // 订单状态列表判断
        if (order.getOrderStates() != null && !order.getOrderStates().isEmpty()) {
            for (OrderState orderState : order.getOrderStates()) {
                if (orderState.getOrderMediaList() != null && !orderState.getOrderMediaList().isEmpty()) {
                    pictures = new ArrayList<>();
                    videos = new ArrayList<>();
                    for (OrderMedia orderMedia : orderState.getOrderMediaList()) {
                        // 图片
                        if (FILE_PICTURE.equals(orderMedia.getMediaType())) {
                            pictures.add(orderMedia);
                        } else if (FILE_VIDEO.equals(orderMedia.getMediaType())) {
                            videos.add(orderMedia);
                        }
                    }
                    orderState.setPictureList(pictures);
                    orderState.setVideoList(videos);
                }
            }
        }
    }

    @Override
    public Map<String, String> getWeChatPayParam(String customerNo, String orderNo, String appType, String ipAddress) throws Exception {
        // 获取类型
        AppTypeEnum appTypeEnum = AppTypeEnum.filter(appType);
        if (appTypeEnum == null) {
            return null;
        }

        // 获取订单金额
        Order order = consignOrderService.getConsignOrderByOrderNo(orderNo);
        if (order == null) {
            return null;
        }

        // 获取openid
        CustomerOpenId customerOpenId = customerOpenIdService.getByCustomerNoAndType(customerNo, appTypeEnum);

        Map<String, String> result = wxPay.pay(new WxPayBody(customerOpenId != null ? customerOpenId.getOpenId() : null,
                WeChatPayCallBackEnum.ORDER_PAY.getCompleteCallBackURL(localHost, order.getOrderNo()),
                order.getOutTradeNo(), WeChatPayCallBackEnum.ORDER_PAY.getOrderDescribe(orderNo),
                order.getPaymentAmount(), ipAddress, WxPayType.filter(appTypeEnum.getPayType())));

        // 更新模板参数
        updatePrepayId(orderNo, result);
        return result;
    }

    @Override
    public Map<String, String> getOtherWeChatPayParam(String customerNo, String orderNo, String appType, String ipAddress) throws Exception {
        // 获取类型
        AppTypeEnum appTypeEnum = AppTypeEnum.filter(appType);
        if (appTypeEnum == null) {
            return null;
        }

        // 获取订单金额
        Order order = consignOrderService.getConsignOrderByOrderNo(orderNo);
        if (order == null) {
            return null;
        }

        // 获取openid
        CustomerOpenId customerOpenId = customerOpenIdService.getByCustomerNoAndType(customerNo, appTypeEnum);

        order.setOutTradeNo(getWeChatOutTradeNo(getCityShortName(order.getTransport().getStartCity())));

        Map<String, String> result = wxPay.pay(new WxPayBody(customerOpenId != null ? customerOpenId.getOpenId() : null,
                WeChatPayCallBackEnum.ORDER_PAY.getCompleteCallBackURL(localHost, order.getOrderNo()),
                order.getOutTradeNo(), WeChatPayCallBackEnum.ORDER_PAY.getOrderDescribe(orderNo),
                order.getPaymentAmount(), ipAddress, WxPayType.filter(appTypeEnum.getPayType())));

        // 更新模板参数
        updatePrepayId(orderNo, result);
        return result;
    }

    /**
     * @return void
     * @Author LiuXiangLin
     * @Description 更新模板消息必须参数
     * @Date 19:12 2019/8/26
     * @Param [payParam]
     **/
    private void updatePrepayId(String orderNo, Map<String, String> payParam) {
        if (payParam != null && !payParam.isEmpty()) {
            String packageStr = payParam.get("package");
            if (packageStr != null && !packageStr.isEmpty()) {
                String[] packageArray = packageStr.split("=");
                if (packageArray.length > 1) {
                    consignOrderMapper.updateOrderPrepayId(orderNo, packageArray[1]);
                }
            }
        }
    }


    @Override
    public int cancelOrder(String orderNo, String customerNo) {
        return consignOrderMapper.updateOrderCancel(orderNo, customerNo, OrderStatusEnum.CANCEL.getState());
    }

    @Override
    public String getOrderByOrder(String orderNo, String customerNo) {
        // 获取职员信息
        Staff staff = staffService.getStaffByCustomerNoAndStatus(customerNo, StaffStateEnum.NORMAL.getType());
        // 如果职员为空 则表示是客户 客户通过openId和订单号查询
        if (staff == null) {
            return consignOrderMapper.getCustomerOrderNoByOrderNo(customerNo, orderNo);
        }
        return consignOrderMapper.getStaffOrderNoByOrderNo(staff, orderNo);
    }

    @Override
    public int updateOrderState(String orderNo, String orderState) {
        return consignOrderMapper.updateOrderState(orderNo, orderState);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int uploadPaymentVoucher(String orderNo, String paymentVoucher) {
        Order order = consignOrderMapper.getOrder(orderNo);
        //订单待付款状态下才可上传收款凭证
        if(OrderStatusEnum.TO_BE_PAID.getState().equals(order.getState())){
            int i = consignOrderMapper.uploadPaymentVoucher(orderNo, paymentVoucher);
            if (i > 0) {
                try {
                    //回调支付成功
                    orderCallBackService.payConsignOrder(orderNo);
                    //添加线下支付订单标识
                    consignOrderMapper.updateOfflinePayment(orderNo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //修改订单状态为付款凭证待审核
            //consignOrderMapper.updateOrderState(orderNo, OrderStatusEnum.TO_BE_REVIEWED.getState());
            return i;
        }
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int examinePaymentVoucher(String orderNo, boolean result, String feedback) {
        Order order = consignOrderMapper.getOrder(orderNo);
        if(!OrderStatusEnum.TO_BE_REVIEWED.getState().equals(order.getState())){
            return 0;
        }
        if(result){ //审核通过
            try {
                //回调支付成功
                orderCallBackService.payConsignOrder(orderNo);
                //添加线下支付订单标识
                consignOrderMapper.updateOfflinePayment(orderNo);
                //记录审核付款凭证反馈
                consignOrderMapper.updateReviewVoucherRemarks(orderNo, "付款凭证审核通过");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{    //审核不通过
            //修改状态为待付款
            consignOrderMapper.updateOrderState(orderNo, OrderStatusEnum.TO_BE_PAID.getState());
            //记录审核付款凭证反馈
            consignOrderMapper.updateReviewVoucherRemarks(orderNo, feedback);
        }
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int confirmOrder(String[] fileList, String orderNo) {
        int result;

        Date now = new Date();
        // 获取订单
        Order order = consignOrderService.getConsignOrderByOrderNo(orderNo);

        // 如果不是待收货则不允许签收订单
        if (!OrderStatusEnum.RECEIVING.getState().equals(order.getState())) {
            return 0;
        }

        // 获取订单的上一个状态
        OrderState orderState = orderStateService.getLastOrderState(orderNo);

        int sn = orderState.getSn();

        // 修改订单状态
        consignOrderMapper.updateOrderState(orderNo, OrderStatusEnum.COMPLETED.getState());

        // 插入流水
        result = orderStateService.saveOrderState(new OrderState(orderNo, ++sn,
                DateUtil.format(now, DateUtil.FORMAT_SIMPLE), DateUtil.format(now, DateUtil.FORMAT_TIME), OrderStateEnum.COMPLETED.getDescription(),
                OrderStateEnum.COMPLETED.getType(), orderState.getStation()));

        // 插入图片列表
        if (fileList != null && fileList.length > 0) {
            ((OrderStateServiceImpl) orderStateService).updateOrderMediaSn(fileList, sn);
        }

        // 站点站内信
        stationMessageService.confirmOrderStationMessage(order);

        // 客户站内信
        customerMessageService.confirmOrderCustomerMessage(order);

        return result;
    }

    @Override
    public List<Order> listByCityNameAndState(String customerNo) {
        // 获取员工信息
        Staff staff = staffService.getStaffByCustomerNoAndStatus(customerNo, StaffStateEnum.NORMAL.getType());
        if (staff != null) {
            return consignOrderMapper.listByCityNameAndState(staff.getStation().getCity(), OrderStatusEnum.RECEIVING.getState());
        }
        return Collections.emptyList();
    }

    @Override
    public int updateOrderPrepayId(String orderNo, String prepayId) {
        return consignOrderMapper.updateOrderPrepayId(orderNo, prepayId);
    }

    @Override
    public Paging<Order> listCompleteOrder(String queryParamStr) throws Exception {
        if (StringUtils.isBlank(queryParamStr)) {
            return null;
        }
        // 将json串转为对象
        StaffOrderQueryDTO staffOrderQueryDTO = JsonUtil.formObject(queryParamStr, StaffOrderQueryDTO.class);
        staffOrderQueryDTO.setCode(staffOrderQueryDTO.getCode() == null ? null : staffOrderQueryDTO.getCode().toUpperCase());

        if (staffOrderQueryDTO != null) {
            // 查询客数据
            Staff staff = staffService.getByStaffNo(Integer.valueOf(staffOrderQueryDTO.getStaffNo()));
            if (staff != null) {
                Paging<Order> paging = new Paging<>();
                int total;
                List<Order> orderList;
                // 判断是否是管理员或客服
                //Station station = stationService.getByPhone(staff.getPhone());
                if (staff.getRole().equals(1)||staff.getRole().equals(2)) {
                    // 是管理员（查询所有的订单）
                    total = consignOrderMapper.countAllAdminOrder(staff);
                    orderList = consignOrderMapper.listAllAdminOrder(staffOrderQueryDTO);
                } else {
                    // 是普通职员 只能看到自己接手过的单子
                    total = consignOrderMapper.countAllStaffOrder(staff);
                    orderList = consignOrderMapper.listAllStaffOrder(staffOrderQueryDTO);
                }
                // 将订单状态进行排序
                if (!CollectionUtils.isEmpty(orderList)) {
                    for (Order order : orderList) {
                        if(order.getTransport()==null||order.getTransport().getTransportNo()==-1){
                            Transport transport = transportService.getTransportByOrderNo(order.getOrderNo());
                            order.setTransport(transport);
                        }
                        // 进行排序
                        if (!CollectionUtils.isEmpty(order.getOrderStates())) {
                            Collections.sort(order.getOrderStates());
                        }
                    }
                }
                paging.setData(orderList);
                paging.setTotalCount(total);

                return paging;
            }
        }

        return null;

    }

    @Override
    public int updateOrderContacts(OrderUpdateDTO orderUpdateDTO) {
        int result = consignOrderMapper.updateContacts(orderUpdateDTO);
        // 如果更新成功 则给站点管理员以及所有已近分配的员工发送站内信
        if (result > 0) {
            // 发送员工的站内信
            customerMessageService.saveOrderContactsUpdateMessage(orderUpdateDTO.getOrderNo());
            // 发送站点的站内信
            stationMessageService.saveOrderContactsUpdateMessage(orderUpdateDTO.getOrderNo());
        }

        return result;
    }

    @Override
    public int checkConfirm(String orderNo, String customerNo) {
        // 是否有权限签收订单
        Integer result = consignOrderMapper.getExistsByOrderNoAndReceiverNo(orderNo, customerNo);
        if (result != null && result == 1) {
            // 只有待收货状态才能签收订单
            Order order = consignOrderService.getConsignOrderByOrderNo(orderNo);
            if (OrderStatusEnum.RECEIVING.getState().equals(order.getState())) {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public List<String> listOrderNoByType(String type, String afterTime, int offset, int limit) {
        return consignOrderMapper.listOrderNoByType(type, afterTime, offset, limit);
    }

    @Override
    public Order getOrderByOutTradeNo(String outTradeNo) {
        return consignOrderMapper.getOrderByOutTradeNo(outTradeNo);
    }

    @Override
    public List<String> listOutTradeNoByType(String type, String afterTime, int offset, int limit) {
        return consignOrderMapper.listOutTradeNoNoByType(type, afterTime, offset, limit);
    }

    @Override
    public int updateStateByOutTrade(String outTradeNo, String orderState) {
        return consignOrderMapper.updateOrderStateByOutTradeNo(outTradeNo, orderState);
    }

    @Override
    public int updatePaymentAmount(Order order) throws Exception{
        // 获取订单
        Order oldOrder = getConsignOrderByOrderNo(order.getOrderNo());
        if (oldOrder != null && oldOrder.getPaymentAmount().compareTo(order.getPaymentAmount()) != 0) {
            // 如果订单金额没有改变则不修改订单支付
            order.setOutTradeNo(getWeChatOutTradeNo(getCityShortName(oldOrder.getTransport().getStartCity())));
            int i = consignOrderMapper.updateOrderPaymentAmount(order);
            consignOrderPriceService.getOrderPaymentAmount(order);
            return i;
        }

        return 0;
    }

    @Override
    public List<Order> listOrderByTypeAndTime(String type, String afterTime, int offset, int limit) {
        return consignOrderMapper.listOrderByTypeAndTime(type, afterTime, offset, limit);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int refundOrder(ConsignOrderRefund consignOrderRefund) {
        // 查询退款单
        List<ConsignOrderRefund> existsList = consignOrderRefundService.listByOrderNo(consignOrderRefund.getOrder().getOrderNo());

        if (!CollectionUtils.isEmpty(existsList)) {
            return 0;
        }

        // 获取订单
        Order order = getConsignOrderByOrderNo(consignOrderRefund.getOrder().getOrderNo());
        if (order == null) {
            return 0;
        }
        // 设置退单对象
        consignOrderRefund.setOrderAmount(order.getPaymentAmount());
        consignOrderRefund.setRefundTime(DateUtil.formatFull(new Date()));
        consignOrderRefund.setRefundAmount(order.getPaymentAmount());
        consignOrderRefund.setRefundOrderState(RefundOrderState.REFUND_COMPLETE.getState());

        // 退款单
        int result = consignOrderRefundService.save(consignOrderRefund);

        Station station = stationService.getByOrderNo(order.getOrderNo());
        if(!getOfflinePayment(order.getOrderNo())){
            // 站点退款
            result += stationBalanceService.saveRefundOrder(order, station, order.getPaymentAmount());
        }

        // 修改订单状态
        consignOrderService.updateOrderState(order.getOrderNo(), OrderStatusEnum.REFUND.getState());

        // 添加订单实时状态
        OrderState lastOrderState = orderStateService.getLastOrderState(order.getOrderNo());
        lastOrderState.setDate(DateUtil.format(new Date(), DateUtil.FORMAT_SIMPLE));
        lastOrderState.setTime(DateUtil.format(new Date(), DateUtil.FORMAT_TIME));
        lastOrderState.setCurrentPosition("订单已经退款");
        lastOrderState.setSn(lastOrderState.getSn() + 1);
        lastOrderState.setOrderType(OrderStateEnum.REFUND.getType());
        orderStateService.saveOrderState(lastOrderState);

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int automaticConfirmConsignOrder(String orderNo) {
        // 获取最后一个状态
        OrderState lastState = orderStateService.getLastOrderState(orderNo);

        // 添加一个状态
        orderStateService.saveOrderState(new OrderState(orderNo,
                lastState.getSn() + 1,
                DateUtil.format(new Date(), DateUtil.FORMAT_SIMPLE), DateUtil.format(new Date(), DateUtil.FORMAT_TIME), "订单自动确认签收", OrderStateEnum.COMPLETED.getType(), lastState.getStation()));

        // 修改订单状态
        this.updateOrderState(orderNo, OrderStatusEnum.COMPLETED.getState());

        return 1;
    }

    @Override
    public List<String> listOutPortOvertime(int offset, int limit) {
        return consignOrderMapper.listByOrderStateAndType(OrderStatusEnum.RECEIVING.getState(), OrderStateEnum.OUT_PORT.getType(), DateUtil.formatFull(DateUtil.getAddHourDate(new Date(), -24 * 2)), offset, limit);
    }

    @Override
    public BigDecimal getOrderPriceByOrderNo(String orderNo) {
        return consignOrderMapper.getPriceByOrderNo(orderNo);
    }

    @Override
    public String getCustomerOrderNoByOrderNo(String orderNo, String customerNo) {
        return consignOrderMapper.getCustomerOrderNoByOrderNo(customerNo, orderNo);
    }

    @Override
    public String getStaffOrderNoByOrderNo(String orderNo, Staff staff) {
        return consignOrderMapper.getStaffOrderNoByOrderNo(staff, orderNo);
    }

    @Override
    public int updateMaxStatusSn(String orderNo, int sn) {
        return consignOrderMapper.updateMaxStateSn(orderNo, sn);
    }

    @Override
    public Order getOrderDetailByOrderNo(String orderNo) {
        Order order = consignOrderMapper.getDetailByOrderNo(orderNo);
        if (order != null) {
            // 分离列表
            separateMedia(order);
            // 进行排序
            Collections.sort(order.getOrderStates());
            // 查询已分配的员工
            order.setOrderAssignments(assignmentService.listByOrderNo(orderNo));
        }

        return order;
    }

    @Override
    public int deleteOrderByOrderNo(String orderNo) {
        return consignOrderMapper.deleteOrderByOrderNo(orderNo);
    }

    @Override
    public int updateOfflinePayment(String orderNo) {
        return consignOrderMapper.updateOfflinePayment(orderNo);
    }

    @Override
    public boolean getOfflinePayment(String orderNo) {
        String offlinePayment = consignOrderMapper.getOfflinePayment(orderNo);
        if(offlinePayment.equals("1")){ //是线下付款订单
            return true;
        }
        return false;
    }

    @Override
    public int confirmRegulation(String orderNo) {
        int i = consignOrderMapper.confirmRegulation(orderNo);
        return i;
    }

    @Override
    public List<OrderVO> listAdminOrder(OrderQueryDto orderQueryDto) {
        List<OrderVO> orderList = consignOrderMapper.listAdminOrder(orderQueryDto);
        return orderList;
    }

    @Override
    public int countListAdminOrder(OrderQueryDto orderQueryDto) {
        int i = consignOrderMapper.countListAdminOrder(orderQueryDto);
        return i;
    }


    @Override
    public Order getConsignOrderByOrderNo(String orderNo) {
        return consignOrderMapper.getConsignOrderByOrderNo(orderNo);
    }


    @Override
    public int updateInsureCode(String orderNo, String insureCode) {
        return consignOrderMapper.updateInsureCode(orderNo, insureCode);
    }

    /**
     * 是否处于区间内
     *
     * @param weight    需要匹配的重量
     * @param minWeight 最小值
     * @param maxWeight 最大值
     * @return boolean
     * @author LiuXiangLin
     * @date 11:20 2019/10/22
     **/
    private boolean inSection(BigDecimal weight, BigDecimal minWeight, BigDecimal maxWeight) {
        return weight.compareTo(minWeight) > 0 && weight.compareTo(maxWeight) <= 0;
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

    @Override
    public Order getOrder(String orderNo) {
        return consignOrderMapper.getOrder(orderNo);
    }


}
