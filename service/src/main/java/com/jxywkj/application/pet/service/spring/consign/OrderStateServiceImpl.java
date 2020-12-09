package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.common.enums.*;
import com.jxywkj.application.pet.common.utils.*;
import com.jxywkj.application.pet.common.utils.wx.sub.WeChatSubMsgDate;
import com.jxywkj.application.pet.dao.consign.ConsignOrderResourceMapper;
import com.jxywkj.application.pet.dao.consign.OrderStateMapper;
import com.jxywkj.application.pet.model.consign.*;
import com.jxywkj.application.pet.model.consign.file.OrderStateTempFiles;
import com.jxywkj.application.pet.service.facade.common.CustomerMessageService;
import com.jxywkj.application.pet.service.facade.common.OrderRebateService;
import com.jxywkj.application.pet.service.facade.common.SmsSendService;
import com.jxywkj.application.pet.service.facade.common.WeChatSubMsgService;
import com.jxywkj.application.pet.service.facade.consign.*;
import com.yangwang.sysframework.utils.DateUtil;
import com.yangwang.sysframework.utils.TypeConvertUtil;
import com.yangwang.sysframework.veriflight.dto.PushFlightResponseData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.xml.ws.ResponseWrapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName OrderStateServiceImpl
 * @Description 订单状态
 * @Author LiuXiangLin
 * @Date 2019/7/22 15:24
 * @Version 1.0
 **/
@Service
public class OrderStateServiceImpl implements OrderStateService {
    @Resource
    OrderStateMapper orderStateMapper;

    @Resource
    FileUtils fileUtils;

    @Resource
    OrderTransportService orderTransportService;

    @Resource
    ConsignOrderLedgerService consignOrderLedgerService;

    @Resource
    OrderMediaService orderMediaService;

    @Resource
    OrderStateService orderStateService;

    @Resource
    ConsignOrderService consignOrderService;

    @Resource
    StationService stationService;

    @Resource
    TempFilesService tempFilesService;

    @Resource
    SmsSendService smsSendService;

    @Resource
    OrderRebateService orderRebateService;

    @Resource
    CustomerMessageService customerMessageService;

    @Resource
    StationMessageService stationMessageService;

    @Resource
    StationBlackListService stationBlackListService;

    @Resource
    AliOssObjectUtils aliOssObjectUtils;

    @Resource
    VariFlightService variFlightService;

    @Resource
    InsureService insureService;

    @Resource
    ConsignInsureFlowService consignInsureFlowService;

    @Resource
    TransportService transportService;

    @Resource
    WeChatSubMsgService weChatSubMsgService;

    @Resource
    ConsignStationTransferService consignStationTransferService;

    @Resource
    ConsignOrderResourceService consignOrderResourceService;

    @Resource
    ConsignOrderResourcePoolService consignOrderResourcePoolService;

    @Resource
    OrderFlowService orderFlowService;

    @Override
    public List<OrderState> listOrderState(String orderNo) {
        return orderStateMapper.listOrderState(orderNo);
    }

    @Override
    public int countByOrderNo(String orderNo) {
        return orderStateMapper.countByOrderNo(orderNo);
    }

    @Override
    public List<OrderState> selectByOrderNo(String orderNo) {
        return orderStateMapper.selectByOrderNo(orderNo);
    }


    @Override
    public List<OrderStateTempFiles> uploadOrderMedia(String orderNo, MultipartFile[] multipartFiles) throws IOException {
        // 上传文件
        List<FileUpLoadState> uploadFileResult = uploadFile(multipartFiles, orderNo);
        // 创建流水
        return addTempFileFlow(orderNo, uploadFileResult);
    }


    /**
     * @return java.util.List<com.jxywkj.application.pet.model.consign.file.OrderStateTempFiles>
     * @Author LiuXiangLin
     * @Description 插入临时表数据
     * @Date 10:27 2019/8/16
     * @Param [orderNo, uploadFileResult]
     **/
    private List<OrderStateTempFiles> addTempFileFlow(String orderNo, List<FileUpLoadState> uploadFileResult) {
        List<OrderStateTempFiles> result = new ArrayList<>();
        /*当前时间*/
        Date now = new Date();
        OrderStateTempFiles orderStateTempFiles;

        if (!uploadFileResult.isEmpty()) {
            for (FileUpLoadState fileUpLoadState : uploadFileResult) {
                // 创建对象
                orderStateTempFiles = new OrderStateTempFiles(orderNo, DateUtil.format(now, DateUtil.FORMAT_SIMPLE),
                        DateUtil.format(now, DateUtil.FORMAT_TIME), fileUpLoadState.getFileAddress(), fileUpLoadState.getMediaFileTypeEnum().getTypeName(), fileUpLoadState.getNewFileName());

                // 插入流水
                tempFilesService.addATempFiles(orderStateTempFiles);

                // 设置访问路径
                orderStateTempFiles.setViewAddress(orderStateTempFiles.getFileAddress());

                result.add(orderStateTempFiles);
            }
        }

        return result;
    }

    /**
     * @return java.util.List<com.jxywkj.application.pet.common.utils.FileUpLoadState>
     * @Author LiuXiangLin
     * @Description 上传文件
     * @Date 10:34 2019/8/16
     * @Param [multipartFiles, orderNo]
     **/
    private List<FileUpLoadState> uploadFile(MultipartFile[] multipartFiles, String orderNo) throws IOException {
        List<FileUpLoadState> result = new ArrayList<>();
        MediaFileTypeEnum mediaFileTypeEnum;

        // 文件流判断
        if (multipartFiles != null && multipartFiles.length > 0) {
            for (MultipartFile multipartFile : multipartFiles) {
                // 获取该文件类型的枚举
                mediaFileTypeEnum = MediaFileTypeEnum.filter(fileUtils.getFileSuffix(multipartFile.getOriginalFilename()));
                // 如果为空则跳出该循环
                if (mediaFileTypeEnum == null) {
                    continue;
                }

                // 上传文件
                result.add(aliOssObjectUtils.uploadImg(multipartFile, mediaFileTypeEnum));
            }
        }
        return result;
    }


    /**
     * @return void
     * @Author LiuXiangLin
     * @Description 回写订单状态
     * @Date 17:03 2019/8/12
     * @Param [orderNo, sn, orderType, orderDate, orderTime]
     **/
    private int writeBackOrder(String orderNo, int sn, String orderType) {
        // 获取订单
        Order order = consignOrderService.getConsignOrderByOrderNo(orderNo);

        // 获取最后一个状态
        OrderState lastOrderState = orderStateMapper.getLastStateByOrderState(orderNo);

        String city = order.getStation()!=null?order.getStation().getCity():"";

        Station endStation = null;
        //获取运输路线
        Transport transport = transportService.getTransportByTransportNo(order.getTransport().getTransportNo());
        if(transport == null){  //非标订单，没有正式的运输路线
            OrderLedger orderLedger = consignOrderLedgerService.getByOrderNo(orderNo);
            transport = Transport.defaultTransport(city,orderLedger.getEndCity(), TypeConvertUtil.$Str(TransportTypeEnum.filterByTypeName(orderLedger.getTransportTypeName())));
        }
        //获取终点站点
        List<Station> endStationList = stationService.listStation(order.getTransport().getEndCity());
        if (endStationList != null && !endStationList.isEmpty()) {
            endStation = endStationList.get(0);
        }


        //获取当前站点
        Station currentStation = stationService.getStation(lastOrderState.getStation().getStationNo());
        //下一站点,默认为终点站点
        Station terminalStation = endStation;

        // 如果是入港或者待揽件操作 则将状态改为已入港 ，准备出港，同时将订单改为待发货
        if (OrderStateEnum.TO_BE_IN_PORT.getType().equals(lastOrderState.getOrderType()) || OrderStateEnum.TO_BE_PACK.getType().equals(lastOrderState.getOrderType())) {

            //揽件操作
            if(order.getStation()!=null&&order.getStation().getStationNo()==currentStation.getStationNo()){

                // 发送微信订阅
                try {
                    weChatSubMsgService.inPort(order);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // 投保
                try {
                    //如果金额大于20才投保
                    if(order.getPaymentAmount().compareTo(BigDecimal.valueOf(20)) > 0){
                        insureService.addDaDiOrderInsure(order);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(order.getPaymentAmount().compareTo(BigDecimal.valueOf(20)) > 0){
                    // 投保流水
                    if (order.joinInsure()) {
                        consignInsureFlowService.save(order);
                    }
                }

            }

            //获取中转站
            StationTransfer transfer = consignStationTransferService.getLastTransferByOrderNo(orderNo);
            if(transfer!=null){
                //下一站点
                terminalStation = stationService.getStation(Integer.valueOf(transfer.getStationNo()));

            }
            // 发送短信消息
            try {
                smsSendService.sendInPortSms(order);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 收件人以及发件人站内信推送
            try {
                customerMessageService.sendInPortCustomerMessage(order);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 收货站点以及发货站点站内信推送
            try {
                stationMessageService.saveInPortMessage(order,currentStation,terminalStation);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 添加一条已经入港的操作
            orderStateService.saveOrderState(getOrderState(orderNo, ++sn, new Date(), currentStation, OrderStateEnum.IN_PORT));
            // 添加一条等待出港
            orderStateService.saveOrderState(getOrderState(orderNo, ++sn, new Date(), currentStation, OrderStateEnum.TO_BE_OUT_PORT));
        }

        // 如果是出港操作 则将状态改为已经出港
        if (OrderStateEnum.TO_BE_OUT_PORT.getType().equals(lastOrderState.getOrderType())) {
            // 将订单改为待收货
            consignOrderService.updateOrderState(orderNo, OrderStatusEnum.RECEIVING.getState());

            // 添加一条已经出港的操作
            orderStateService.saveOrderState(getOrderState(orderNo, ++sn, new Date(), currentStation, OrderStateEnum.OUT_PORT));
            ++sn;

            //获取中转站
            StationTransfer transfer = consignStationTransferService.getLastTransferByOrderNo(orderNo);

            //如果有中转站，就添加一个待入港
            if(transfer!=null){
                //起始站点的出港
                if(order.getStation()!=null&&order.getStation().getStationNo()==currentStation.getStationNo()){
                    // 商家返利,不存在中转站，只有1个出港
                    this.saveRetabe(orderNo,currentStation);
                    //下一站点
                    terminalStation = stationService.getStation(Integer.valueOf(transfer.getStationNo()));
                    // 获取黑名单对象
                    StationBlacklist stationBlacklist = stationBlackListService.getByBlackStationNo(currentStation.getStationNo(), terminalStation.getStationNo());
                    // 黑名单对象等于空则表示没有被拉入黑名单
                    if (stationBlacklist == null) {
                        orderStateService.saveOrderState(getOrderState(orderNo, sn, new Date(), terminalStation, OrderStateEnum.TO_BE_IN_PORT));
                    }
                }else{
                    //中转站点出港
                    // 获取黑名单对象
                    StationBlacklist stationBlacklist = stationBlackListService.getByBlackStationNo(currentStation.getStationNo(), endStation==null?null:endStation.getStationNo());
                    // 黑名单对象等于空则表示没有被拉入黑名单
                    if (stationBlacklist == null) {
                        //添加一个即将到达终点站点的操作
                        orderStateService.saveOrderState(getOrderState(orderNo, sn, new Date(), endStation, OrderStateEnum.TO_BE_ARRIVED));
                    }
                }
            }else{  //没有中转站
                // 获取黑名单对象
                StationBlacklist stationBlacklist = stationBlackListService.getByBlackStationNo(currentStation.getStationNo(), endStation==null?-1:endStation.getStationNo());
                // 黑名单对象等于空则表示没有被拉入黑名单
                if (stationBlacklist == null) {
                    //添加一个即将到达终点站点的操作
                    orderStateService.saveOrderState(getOrderState(orderNo, sn, new Date(), endStation, OrderStateEnum.TO_BE_ARRIVED));
                }
                // 商家返利,不存在中转站，只有1个出港
                this.saveRetabe(orderNo,currentStation);
            }

            // 给收件人以及发件人发送短信提醒
            try {
                smsSendService.sendOutPortMsg(order, TypeConvertUtil.$int(transport.getTransportType()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 给收件人以及收件人发送站信推送
            try {
                customerMessageService.sensOutPortCustomerMessage(order);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //TODO 给下一站点发送短信提醒
            try {
                smsSendService.sendOutPortMsgToNextStation(order, TypeConvertUtil.$int(transport.getTransportType()),terminalStation==null?0:terminalStation.getStationNo());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 给站点发送站内信推送
            try {
                stationMessageService.saveOutPortMessage(order,currentStation,terminalStation);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 发送微信订阅消息
            try {
                weChatSubMsgService.outPort(order);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 订阅飞常准
            try {
                variFlightService.orderSubscribe(order);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        // 如果是到达操作 则判断是否有送宠上门的操作，如果有则改为派送中，如果没有改为待签收
        if (OrderStateEnum.TO_BE_ARRIVED.getType().equals(lastOrderState.getOrderType())) {
            // 已经到达
            orderStateService.saveOrderState(getOrderState(orderNo, ++sn, new Date(), endStation, OrderStateEnum.ARRIVED));
            // 如果有上门送宠
            if (order.getSendAddress() != null && !StringUtils.isBlank(order.getSendAddress())) {
                orderStateService.saveOrderState(getOrderState(orderNo, ++sn, new Date(), endStation, OrderStateEnum.DELIVERING));
            } else {
                // 没有配置上门送宠
                orderStateService.saveOrderState(getOrderState(orderNo, ++sn, new Date(), endStation, OrderStateEnum.TO_BE_SIGN));
            }

            // 给收件人已经发件人发送短信提醒
            try {
                smsSendService.sendArrivedSms(order);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 客户站内信
            try {
                customerMessageService.sendArriveCustomerMessage(order);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 商家站内信
            try {
                stationMessageService.saveArriveMessage(order,currentStation);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 微信订阅消息
            try {
                weChatSubMsgService.arrive(order);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return --sn;
    }

    /**
     * @return com.jxywkj.application.pet.model.consign.OrderState
     * @Author LiuXiangLin
     * @Description 创建订单状态对象
     * @Date 10:39 2019/8/16
     * @Param [orderNo, sn, orderDate, orderTime, order]
     **/
    public OrderState getOrderState(String orderNo, int sn, Date date, Station station, OrderStateEnum orderStateEnum) {
        String currentPosition = "";
        switch (orderStateEnum.getType()){
            case "待入港":
                currentPosition += "订单等待入港";
                break;
            case "已入港":
                currentPosition += (station.getStationName()==null?"订单揽件成功":"订单已由"+station.getStationName()+"入港");
                break;
            case "待出港":
                currentPosition += "订单等待出港,准备前往下一站点";
                break;
            case "已出港":
                currentPosition += "订单已由"+station.getStationName()+"出港,前往下一站点";
                break;
            default:
                currentPosition += orderStateEnum.getDescription();
        }



        OrderState newOrderState = new OrderState();
        newOrderState.setOrderNo(orderNo);
        newOrderState.setSn(sn);
        newOrderState.setDate(DateUtil.format(date, DateUtil.FORMAT_SIMPLE));
        newOrderState.setTime(DateUtil.format(date, DateUtil.FORMAT_TIME));
        newOrderState.setStation(station);
        newOrderState.setOrderType(orderStateEnum.getType());
        newOrderState.setCurrentPosition(currentPosition);
        return newOrderState;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveOrderState(OrderState orderState) {
        // 新增一条数据
        orderStateMapper.saveOrderState(orderState);
        // 回写订单状态
        consignOrderService.updateMaxStatusSn(orderState.getOrderNo(), orderState.getSn());
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addInPortState(String[] filesAddress, String orderType, String orderNo, int sn) {
        /*
         * 流程
         * 1、获取所有的临时文件
         * 2、遍历判断是否都需要上传了
         * 3、删除不需要上传的文件
         * 4、删除临时表中的所有数据
         * 5、添加订单出港入港图片和视频
         * 6、订单回写
         * */

        // 回写订单状态
        int returnSn = writeBackOrder(orderNo, sn, orderType);
        OrderState orderState = orderStateMapper.getLastStateByOrderState(orderNo);
        //如果有对应存在的资源池节点，把资源池的资源移入出入港流水中，并删除资源池资源
        List<OrderResourcePool> pools = null;
        if("待出港".equals(orderType)) { //出港触发出港后节点
            //查询资源池中是否有对应资源
            pools = consignOrderResourcePoolService.listByOrderNoAndNode(orderNo, "出港后");
        }else if("待入港".equals(orderType)||"待到达".equals(orderType)){  //入港触发当前站点的出港前和上一站点的到达下个站点节点
            //获取对应资源
            pools = consignOrderResourcePoolService.listByOrderNoAndNode(orderNo, "出港前");
            List<OrderResourcePool> list = consignOrderResourcePoolService.listByOrderNoAndNode(orderNo, "到达下个站点");
            for(OrderResourcePool orderResourcePool : list){
                if(!orderResourcePool.getStationNo().equals(String.valueOf(orderState.getStation().getStationNo()))){
                    pools = (pools == null? pools=new ArrayList<>():pools);
                    pools.add(orderResourcePool);
                }
            }
        }
        if(pools != null && pools.size()>0){
            //创建一个线程池
            ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
            for(OrderResourcePool pool:pools){
                //如果没有延迟设置为延时0秒
                pool.setDelayTime(pool.getDelayTime()==null?0:pool.getDelayTime());
                System.err.println("scheduledExecutorService.schedule()");
                //延时指定的时间执行
                scheduledExecutorService.schedule(new Runnable() {
                    @Override
                    public void run() {
                        //把资源池的资源加入到出入港的流水中
                        System.err.println("执行了线程池中的run方法");
                        List<OrderResource> orderResources = consignOrderResourceService.listByResourcePoolNo(String.valueOf(pool.getResourcePoolNo()));
                        List<OrderMedia> orderMediaList = new ArrayList<>();
                        for(OrderResource orderResource:orderResources){
                            orderMediaList.add(new OrderMedia(StringUtils.getUuid(),orderNo,returnSn,
                                    orderResource.getResourceAddress(),orderResource.getDate(),
                                    orderResource.getTime(), orderResource.getResourceType(),
                                    orderResource.getResourceName()));
                        }
                        orderMediaService.addAMediaList(orderMediaList);
                        //删除已加入出入港流水的资源池资源
                        consignOrderResourceService.deleteByResourcePoolNo(String.valueOf(pool.getResourcePoolNo()));
                        consignOrderResourcePoolService.deleteById(pool.getResourcePoolNo());
                    }
                }, pool.getDelayTime(), TimeUnit.MILLISECONDS);
            }
            return 1;
        }

        return updateOrderMediaSn(filesAddress, returnSn);
    }


    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 更新订单的SN
     * @Date 16:35 2019/9/21
     * @Param [filesAddress, sn]
     **/
    int updateOrderMediaSn(String[] filesAddress, int sn) {
        if (filesAddress != null && filesAddress.length > 0) {
            // 获取图片对象
            for (String address : filesAddress) {
                OrderMedia orderMedia = orderMediaService.getByAddress(address);
                if (orderMedia != null) {
                    orderMedia.setSn(sn);
                    orderMediaService.updateOrderMediaSn(orderMedia);
                }
            }
            return filesAddress.length;
        }
        return 0;
    }


    /**
     * @return void
     * @Author LiuXiangLin
     * @Description 添加订单状态图片
     * @Date 10:58 2019/8/23
     * @Param [filesAddress, orderNo, sn]
     **/
    public void addOrderStateMedia(String[] filesAddress, String orderNo, int sn) {
        // 添加列表
        List<OrderMedia> orderMediaList = new ArrayList<>();

        // 获取所有的临时文件
        List<OrderStateTempFiles> orderStateTempFiles = tempFilesService.listByOrderNo(orderNo);

        // 删除无用的文件 并且筛选出需要的文件
        deleteUselessFile(filesAddress, orderNo, sn, orderMediaList, orderStateTempFiles);

        // 插入数据
        orderMediaService.addAMediaList(orderMediaList);
    }

    @Override
    public int addPayState(String orderNo) {
        int result = 0;

        // 获取最大SN
        int lastSn = orderStateMapper.getLastSn(orderNo);

        // 获取订单
        Order order = consignOrderService.getConsignOrderByOrderNo(orderNo);

        //通过orderNo获取站点
        Station station = stationService.getByOrderNo(orderNo);

        // 已经支付流水
        Date now = new Date();
        OrderState orderStatePaid = new OrderState(orderNo, ++lastSn, DateUtil.format(now, DateUtil.FORMAT_SIMPLE),
                DateUtil.format(now, DateUtil.FORMAT_TIME), OrderStateEnum.PAID.getDescription(),
                OrderStateEnum.PAID.getType(), station);
        result += orderStateMapper.saveOrderState(orderStatePaid);

        // 等待揽件流水
        OrderState orderStateInPort = getOrderState(orderNo, ++lastSn, new Date(), station, OrderStateEnum.TO_BE_PACK);
        result += orderStateMapper.saveOrderState(orderStateInPort);

        // 回写订单sn
        consignOrderService.updateMaxStatusSn(orderNo, orderStateInPort.getSn());

        return result;
    }

    @Override
    public OrderState getLastOrderState(String orderNo) {
        return orderStateMapper.getLastStateByOrderState(orderNo);
    }

    @Override
    public List<OrderStateTempFiles> addOrderMedia(String orderNo, MultipartFile[] multipartFiles,String node, Long delayTime, String remarks) throws IOException {
        // 上传文件
        List<FileUpLoadState> uploadFileResult = uploadFile(multipartFiles, orderNo);

        if (!CollectionUtils.isEmpty(uploadFileResult)) {
            //获取订单状态
            OrderState orderState = orderStateMapper.getLastStateByOrderState(orderNo);
            //如果订单是待出港，并且节点不为立即上传
            if(node!=null && orderState.getOrderType().equals(OrderStateEnum.TO_BE_OUT_PORT.getType())
                &&(!node.equals("立即上传"))){  //出港操作选择出港前从当前时间开始算延时
                List<OrderStateTempFiles> files = addOrderResourcePool(orderNo, uploadFileResult, node, delayTime, remarks, String.valueOf(orderState.getStation().getStationNo()),orderState);
                return files;
            }
            return addOrderInPortMedia(orderNo, uploadFileResult);
        }
        return null;
    }

    @Override
    public int addDelivering(String orderNo) {
        // 获取最后一个状态
        OrderState lastOrderState = orderStateMapper.getLastStateByOrderState(orderNo);

        // 时间
        Date now = new Date();
        OrderState orderState = new OrderState();

        orderState.setOrderNo(orderNo);
        orderState.setTime(DateUtil.format(new Date(), DateUtil.FORMAT_FULL));
        orderState.setOrderType(OrderStateEnum.DELIVERING.getType());
        orderState.setCurrentPosition(OrderStateEnum.DELIVERING.getDescription());
        orderState.setSn(lastOrderState.getSn() + 1);
        orderState.setStation(lastOrderState.getStation());
        orderState.setTime(DateUtil.format(now, DateUtil.FORMAT_TIME));
        orderState.setDate(DateUtil.format(now, DateUtil.FORMAT_SIMPLE));

        // 回写订单sn
        consignOrderService.updateMaxStatusSn(orderNo, orderState.getSn());

        return orderStateMapper.saveOrderState(orderState);
    }

    @Override
    public int updateVariflightState(Order order, PushFlightResponseData pushFlightResponseData) {
        // 获取最后一个状态
        OrderState orderState = getLastOrderState(order.getOrderNo());
        if (orderState != null) {
            orderState.setSn(orderState.getSn() + 1);
            orderState.setCurrentPosition("当前航班为"
                    + pushFlightResponseData.getFlightCompany() +
                    pushFlightResponseData.getFlightNo() + "号航班，" +
                    "预计起飞时间为" + pushFlightResponseData.getFlightDeptimePlanDate() +
                    "，预计到达时间为" + pushFlightResponseData.getFlightArrtimePlanDate() +
                    "，当前航班状态为" + pushFlightResponseData.getFlightState());
            orderState.setDate(DateUtil.format(new Date(), DateUtil.FORMAT_SIMPLE));
            orderState.setTime(DateUtil.format(new Date(), DateUtil.FORMAT_TIME));

            // 回写订单sn
            consignOrderService.updateMaxStatusSn(orderState.getOrderNo(), orderState.getSn());

            return orderStateMapper.saveOrderState(orderState);

        }

        return 0;
    }

    /**
     * @Description: 添加站点返利
     * @Author: zxj
     * @Date: 2020/9/19 9:38
     * @param orderNo:
     * @param station:
     * @return: void
     **/
    private void saveRetabe(String orderNo,Station station){
        //查看当前订单在站点下是否有流水记录
        OrderFlow orderFlow = orderFlowService.getByOrderNoAndStationNo(orderNo, TypeConvertUtil.$Str(station.getStationNo()));
        //没有当前订单流水记录
        orderFlow = orderFlow==null||orderFlow.getOrderFlowDetails()==null||orderFlow.getOrderFlowDetails().size()==0?null:orderFlow;
        if(orderFlow != null){
            //有流水记录且为下单记录 flag = true
            boolean flag = orderFlow.getOrderFlowDetails().get(0).getFlowType().equals(BalanceFlowTypeEnum.CONSING_ORDER_STATION.getType());
            //orderFlow为null表示没有下单流水记录
            orderFlow = flag?orderFlow:null;
        }
        //如果不是线下付款订单且没有流水下单记录才有商家返利
        if(!consignOrderService.getOfflinePayment(orderNo)&&orderFlow==null){
            // 商家返利
            orderRebateService.saveRebate(orderNo);
        }
    }

    private List<OrderStateTempFiles> addOrderResourcePool(String orderNo, List<FileUpLoadState> uploadFileResult,String node, Long delayTime, String remarks,String stationNo,OrderState orderState){

        // 时间
        Date now = new Date();
        String date = DateUtil.format(now, DateUtil.FORMAT_SIMPLE);
        String time = DateUtil.format(now, DateUtil.FORMAT_TIME);
        //创建一个线程池
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        if("出港前".equals(node)&&OrderStateEnum.TO_BE_OUT_PORT.getType().equals(orderState.getOrderType())) {
            //延时指定的时间执行
            scheduledExecutorService.schedule(new Runnable() {
                @Override
                public void run() {
                    addOrderInPortMedia(orderNo, uploadFileResult);
                }
            }, delayTime==null?0:delayTime, TimeUnit.MILLISECONDS);
            //返回文件地址，以便执行出入港操作
            List<OrderStateTempFiles> lists = new ArrayList<>();
            for (FileUpLoadState file : uploadFileResult) {
                lists.add(new OrderStateTempFiles(orderNo, null, null, file.getFileAddress(), null, file.getNewFileName(), null));
            }
            return lists;
        }else{  //节点选择为出港后或出港前，把资源保存入资源池
            OrderResourcePool orderResourcePool = new OrderResourcePool(orderNo, node, delayTime, remarks,stationNo);
            //添加至资源池
            int i = consignOrderResourcePoolService.save(orderResourcePool);
            //返回文件地址，以便执行出入港操作
            List<OrderStateTempFiles> lists = new ArrayList<>();
            for(FileUpLoadState file: uploadFileResult){
                OrderResource orderResource = new OrderResource(file.getFileAddress(), orderResourcePool,
                        date, time, file.getMediaFileTypeEnum().getTypeName(),file.getNewFileName());
                consignOrderResourceService.save(orderResource);
                lists.add(new OrderStateTempFiles(orderNo, null, null, file.getFileAddress(), null, file.getNewFileName(), null));
            }
            return lists;
        }
    }

    /**
     * @return void
     * @Author LiuXiangLin
     * @Description 删除无用的文件 已经筛选出要添加的数据
     * @Date 10:54 2019/8/16
     * @Param [filesAddress, orderNo, sn, orderMediaList, orderStateTempFiles]
     **/
    private void deleteUselessFile(String[] filesAddress, String orderNo, int sn, List<OrderMedia> orderMediaList, List<OrderStateTempFiles> orderStateTempFiles) {
        Date now = new Date();
        // 删除文件集合
        List<String> deleteList = new ArrayList<>();

        // 保存文件
        OrderMedia tempOrderMedia;

        // 如果没有上传文件 则删除所有的数据
        if (filesAddress == null || filesAddress.length == 0) {
            fileUtils.deletOrderFolder(orderNo);
            tempFilesService.deleteByOrderNo(orderNo);
            return;
        }

        // 筛选出要删除的数据以及要上传的数据
        if (orderStateTempFiles != null && !orderStateTempFiles.isEmpty()) {
            for (String filAddress : filesAddress) {
                int index = 0;
                for (OrderStateTempFiles tempFiles : orderStateTempFiles) {
                    if (filAddress.equals(tempFiles.getViewAddress())) {
                        tempOrderMedia = new OrderMedia(StringUtils.getUuid(), tempFiles.getOrderNo(), sn,
                                tempFiles.getFileAddress(), DateUtil.format(now, DateUtil.FORMAT_SIMPLE),
                                DateUtil.format(now, DateUtil.FORMAT_TIME), tempFiles.getFileType(), tempFiles.getFileName());
                        orderMediaList.add(tempOrderMedia);
                        break;
                    }
                    if (index == orderStateTempFiles.size() - 1) {
                        deleteList.add(tempFiles.getFileAddress());
                    }

                    index++;
                }
            }
        }

        // 删除数据和文件
        if (!deleteList.isEmpty()) {
            String[] deleteArray = new String[deleteList.size()];
            fileUtils.deleteOrderFolderFils(orderNo, deleteList.toArray(deleteArray));
        }
        tempFilesService.deleteByOrderNo(orderNo);
    }


    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 将图片与已入港相关联
     * @Date 11:36 2019/9/21
     * @Param [orderNo, fileUpLoadStates]
     **/
    private List<OrderStateTempFiles> addOrderInPortMedia(String orderNo, List<FileUpLoadState> fileUpLoadStates) {
        // 获取订单待出港、待入港、即将到达的状态
        OrderState orderState = orderStateMapper.getLastStateByOrderState(orderNo);
        List<OrderStateTempFiles> result = null;
        if (orderState != null) {
            // 将所有的图片于这个相关联
            List<OrderMedia> orderMediaList = new ArrayList<>();
            // 时间
            Date now = new Date();
            String date = DateUtil.format(now, DateUtil.FORMAT_SIMPLE);
            String time = DateUtil.format(now, DateUtil.FORMAT_TIME);
            // 临时数据
            OrderStateTempFiles orderStateTempFiles;
            result = new ArrayList<>();
            for (FileUpLoadState fileUpLoadState : fileUpLoadStates) {
                orderMediaList.add(new OrderMedia(StringUtils.getUuid(),
                        orderNo, orderState.getSn(),
                        fileUpLoadState.getFileAddress(),
                        date, time, fileUpLoadState.getMediaFileTypeEnum().getTypeName(),
                        fileUpLoadState.getNewFileName()));


                // 创建对象
                orderStateTempFiles = new OrderStateTempFiles(orderNo, date, time, fileUpLoadState.getFileAddress(),
                        fileUpLoadState.getMediaFileTypeEnum().getTypeName(), fileUpLoadState.getNewFileName());

                // 设置访问路径
                orderStateTempFiles.setViewAddress(orderStateTempFiles.getFileAddress());
                result.add(orderStateTempFiles);

            }
            orderMediaService.addAMediaList(orderMediaList);
        }

        return result;
    }
}
