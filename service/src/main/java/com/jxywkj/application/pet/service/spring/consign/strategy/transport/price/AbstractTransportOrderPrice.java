package com.jxywkj.application.pet.service.spring.consign.strategy.transport.price;

import com.jxywkj.application.pet.common.utils.CollectionUtils;
import com.jxywkj.application.pet.common.utils.StringUtils;
import com.jxywkj.application.pet.model.consign.*;
import com.jxywkj.application.pet.model.consign.params.AddedOnDoorAmount;
import com.jxywkj.application.pet.model.consign.params.LonAndLat;
import com.jxywkj.application.pet.model.consign.params.OrderDTO;
import com.jxywkj.application.pet.model.consign.params.OrderPrice;
import com.jxywkj.application.pet.service.facade.consign.*;
import com.jxywkj.application.pet.service.spring.consign.GaoDeMapUtils;
import com.yangwang.sysframework.utils.TypeConvertUtil;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalCoordinates;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 运输价格计算
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className AbstractTransportOrderPrice
 * @date 2020/4/10 9:33
 **/
public abstract class AbstractTransportOrderPrice {
    @Resource
    AddedVolumeCageService addedVolumeCageService;

    @Resource
    GaoDeMapUtils gaoDeMapUtils;

    @Resource
    AddedReceiptService addedReceiptService;

    @Resource
    AddedSendService addedSendService;

    @Resource
    StationService stationService;

    @Resource
    PositionService positionService;

    private static GeodeticCalculator geodeticCalculator = null;

    {
        geodeticCalculator = new GeodeticCalculator();
    }

    /***
     * <p>
     * 计算订单价格
     * </p>
     *
     * @param transport 运输路线
     * @param orderDTO 订单DTO
     * @return com.jxywkj.application.pet.model.consign.params.OrderPrice
     * @author LiuXiangLin
     * @date 9:35 2020/4/10
     **/
    public abstract OrderPrice calcOrderPrice(Transport transport, OrderDTO orderDTO, Station station) throws Exception;

    /**
     * <p>
     * 获取笼具配置
     * </p>
     *
     * @return com.jxywkj.application.pet.model.consign.AddedVolumeCage
     * @author LiuXiangLin
     * @date 9:44 2020/4/10
     **/
    public AddedVolumeCage getCage(Station station) {
        AddedVolumeCage result = null;

        // 获取该路线的笼具配置
        List<AddedVolumeCage> addedVolumeCages = addedVolumeCageService.listByTransportNo(getTransport().getTransportNo(),station.getStationNo());
        if (!CollectionUtils.isEmpty(addedVolumeCages)) {
            // 将笼具进行重小到达的排序
            Collections.sort(addedVolumeCages);
            // 找到匹配的笼具
            for (AddedVolumeCage addedVolumeCage : addedVolumeCages) {
                if (inSection(getOrderDTO().getWeight(), addedVolumeCage.getAddedWeightCage().getMinWeight(), addedVolumeCage.getAddedWeightCage().getMaxWeight())) {
                    result = addedVolumeCage;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * <p>
     * 计算保价金额
     * </p>
     *
     * @param orderPrice 订单价格对象
     * @param orderDTO   订单DTO
     * @return com.jxywkj.application.pet.model.consign.params.OrderPrice
     * @author LiuXiangLin
     * @date 11:36 2020/4/10
     **/
    public OrderPrice calcInsure(OrderPrice orderPrice, OrderDTO orderDTO) {
        if (orderDTO.getPetAmount() != null && orderDTO.getPetAmount().compareTo(BigDecimal.ZERO) > 0) {
            AddedInsure addedInsure = new AddedInsure();
            addedInsure.setRate(BigDecimal.valueOf(0.01));
            addedInsure.setInsureAmount(orderDTO.getPetAmount().multiply(addedInsure.getRate()).setScale(2, BigDecimal.ROUND_HALF_DOWN));
            orderPrice.setOrderAmount(orderPrice.getOrderAmount().add(addedInsure.getInsureAmount()));
            orderPrice.setAddedInsure(addedInsure);
        }
        return orderPrice;
    }

    /**
     * <p>
     * 计算笼具价格
     * </p>
     *
     * @param orderPrice   订单价格对象
     * @param transportDTO 运输路线DTO
     * @return com.jxywkj.application.pet.model.consign.params.OrderPrice
     * @author LiuXiangLin
     * @date 14:32 2020/4/10
     **/
    public OrderPrice calcCage(OrderPrice orderPrice, TransportDTO transportDTO) {
        // 笼具重量
        BigDecimal cageWeight = Boolean.TRUE.equals(transportDTO.getExistCage()) ? transportDTO.getAddedWeightCage().getCageWeight() : BigDecimal.ZERO;

        // 超出的重量 = 宠物重量 + 笼具重量 - 起步重量（如果存在）
        BigDecimal overWeight = transportDTO.getPetWeight().add(cageWeight).subtract(transportDTO.getStartingWeight());
        overWeight = overWeight.compareTo(BigDecimal.ZERO) > 0 ? overWeight : BigDecimal.ZERO;

        // 超出的费用 = 超出的重量 * 超出加价
        BigDecimal overAmount = overWeight.multiply(transportDTO.getContinuePrice());

        // 按体积算
        if (Boolean.TRUE.equals(transportDTO.getUseVolume())) {
            orderPrice.setOrderAmount(orderPrice.getOrderAmount().add(transportDTO.getVolumeCageAmount()));
        } else {
            // 计算底价
            orderPrice.setOrderAmount(orderPrice.getOrderAmount().add(transportDTO.getStartingPrice()));
            // 超出价格
            orderPrice.setOrderAmount(orderPrice.getOrderAmount().add(overAmount));
        }

        // 如果购买了航空箱
        if (Boolean.TRUE.equals(transportDTO.getBuyCage())) {
            BigDecimal cagePrice = transportDTO.getAddedWeightCage() != null ? TypeConvertUtil.$BigDecimal(transportDTO.getAddedWeightCage().getCagePrice()) : new BigDecimal(0);
            orderPrice.setOrderAmount(orderPrice.getOrderAmount().add(cagePrice));
            orderPrice.setAddedWeightCage(transportDTO.getAddedWeightCage());
        }

        return orderPrice;
    }

    /**
     * <p>
     * 计算上门接宠的价格
     * </p>
     *
     * @return java.math.BigDecimal
     * @author LiuXiangLin
     * @Param startStation 起始站点
     * @date 16:44 2020/4/15
     **/
    public AddedOnDoorAmount calcReceiptPrice(Station startStation) throws Exception {
        // 上门接宠对象
        AddedOnDoorAmount result = new AddedOnDoorAmount();

        // 价格
        BigDecimal receiptAmount = BigDecimal.ZERO;

        // 如果购买了送宠上门
        if (!StringUtils.isBlank(getOrderDTO().getReceiptAddress())) {
            // 创建目标位置对象
            GlobalCoordinates target = newTarget(getOrderDTO().getReceiptAddress());
            result.setLat(target.getLatitude());
            result.setLnt(target.getLongitude());

            // 获取起始站点送宠上门站点配置项
            List<AddedReceipt> addedReceiptList = addedReceiptService.listByStationNo(startStation.getStationNo());
            // 如果存在送宠上门配置以及存在终点站点
            if (!CollectionUtils.isEmpty(addedReceiptList)) {
                // 创建终点位置经纬度对象
                Station station = stationService.getStation(startStation.getStationNo());
                GlobalCoordinates source = new GlobalCoordinates(station.getLat(), station.getLng());

                // 获取距离
                BigDecimal distance = calcDistance(source, target);
                result.setDistance(distance);
                // 计算价格
                receiptAmount = calcPrice(addedReceiptList, distance);
            }
        }

        result.setAmount(receiptAmount);

        return result;
    }

    /**
     * <p>
     * 计算送宠上门价格
     * </p>
     *
     * @param
     * @return java.math.BigDecimal
     * @author LiuXiangLin
     * @date 16:46 2020/4/15
     **/
    public AddedOnDoorAmount calcSendPrice() throws Exception {
        AddedOnDoorAmount result = new AddedOnDoorAmount();

        BigDecimal receiptAmount = BigDecimal.ZERO;
        // 如果购买了送宠上门
        if (!StringUtils.isBlank(getOrderDTO().getSendAddress())) {
            // 创建目标位置对象
            GlobalCoordinates target = newTarget(getOrderDTO().getSendAddress());
            result.setLnt(target.getLongitude());
            result.setLat(target.getLatitude());

            // 获取终点站点列表
            List<Station> endStationList = stationService.listStation(getTransport().getEndCity());

            // 上门接宠配置列表
            List<AddedSend> addedSendList = null;

            // 位置配置项
            Position position = null;

            // 如果存在终点站点
            if (!CollectionUtils.isEmpty(endStationList)) {
                // 获取终点站点配置项
                addedSendList = addedSendService.listByStationNo(endStationList.get(0).getStationNo());

                // 获取终点站点位置配置项
                position = positionService.getByStationNoAndType(endStationList.get(0).getStationNo(), Integer.valueOf(getTransport().getTransportType()));
            }

            // 如果存在配置
            if (!CollectionUtils.isEmpty(addedSendList) && !CollectionUtils.isEmpty(endStationList) && position != null) {
                // 创建起始位置对象
                GlobalCoordinates source = new GlobalCoordinates(position.getLatitude(), position.getLongitude());

                // 计算价格
                BigDecimal distance = calcDistance(source, target);
                result.setDistance(distance);
                receiptAmount = calcPrice(addedSendList, distance);
            }
        }

        result.setAmount(receiptAmount);

        return result;
    }


    /**
     * <p>
     * 计算两点位置
     * </p>
     *
     * @param source 源位置
     * @param target 目标位置
     * @return java.math.BigDecimal
     * @author LiuXiangLin
     * @date 9:13 2020/4/16
     **/
    private BigDecimal calcDistance(GlobalCoordinates source, GlobalCoordinates target) {
        return BigDecimal.valueOf(geodeticCalculator.calculateGeodeticCurve(Ellipsoid.Sphere, source, target).getEllipsoidalDistance());
    }

    /**
     * <p>
     * 创建起始位置经纬度对象
     * </p>
     *
     * @param targetAddress 起始位置
     * @return org.gavaghan.geodesy.GlobalCoordinates
     * @author LiuXiangLin
     * @date 9:21 2020/4/16
     **/
    private GlobalCoordinates newTarget(String targetAddress) throws Exception {
        LonAndLat lonAndLat = gaoDeMapUtils.getLonAndLat(targetAddress);
        return new GlobalCoordinates(Double.valueOf(lonAndLat.getLatitude()), Double.valueOf(lonAndLat.getLongitude()));
    }

    /**
     * <p>
     * 计算价格
     * </p>
     *
     * @param abstractAddedOnDoors 配置
     * @param distance             距离
     * @return java.math.BigDecimal
     * @author LiuXiangLin
     * @date 9:13 2020/4/16
     **/
    private BigDecimal calcPrice(List<? extends AbstractAddedOnDoor> abstractAddedOnDoors, BigDecimal distance) {
        BigDecimal result = BigDecimal.ZERO;
        distance = distance.divide(BigDecimal.valueOf(1000), BigDecimal.ROUND_HALF_UP);

        // 如果是按梯度计算
        if (abstractAddedOnDoors.size() == 1 && abstractAddedOnDoors.get(0).getEndDistance() == null) {
            result = distance.multiply(abstractAddedOnDoors.get(0).getPrice()).divide(BigDecimal.valueOf(1000), 2, BigDecimal.ROUND_DOWN);
        } else {
            Collections.sort(abstractAddedOnDoors);
            // 按里程计算
            int i = 0;
            for (AbstractAddedOnDoor abstractAddedOnDoor : abstractAddedOnDoors) {
                if (distance.compareTo(abstractAddedOnDoor.getStartDistance()) >= 0 && distance.compareTo(abstractAddedOnDoor.getEndDistance()) < 0) {
                    result = abstractAddedOnDoor.getPrice();
                    break;
                }

                if (i == abstractAddedOnDoors.size() - 1) {
                    result = abstractAddedOnDoor.getPrice();
                }
                i++;
            }
        }

        return result;
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
     * 获取订单DTO对象
     * </p>
     *
     * @param
     * @return com.jxywkj.application.pet.model.consign.params.OrderDTO
     * @author LiuXiangLin
     * @date 9:50 2020/4/10
     **/
    public abstract OrderDTO getOrderDTO();

    /**
     * <p>
     * 获取运输路线对象
     * </p>
     *
     * @param
     * @return com.jxywkj.application.pet.model.consign.Transport
     * @author LiuXiangLin
     * @date 9:51 2020/4/10
     **/
    public abstract Transport getTransport();


    class TransportDTO {
        /**
         * 起步价
         */
        private BigDecimal startingPrice;
        /**
         * 续重价
         */
        private BigDecimal continuePrice;

        /**
         * 起步重量
         */
        private BigDecimal startingWeight;

        /**
         * 是否是按体积算
         */
        private Boolean useVolume;

        /**
         * 是否购买航空箱
         */
        private Boolean buyCage;

        /**
         * 是否存在航空箱
         */
        private Boolean existCage;

        /**
         * 宠物重量
         */
        private BigDecimal petWeight;

        /**
         * 笼具
         */
        private AddedWeightCage addedWeightCage;

        /**
         * 体积箱价格
         */
        private BigDecimal volumeCageAmount;

        BigDecimal getContinuePrice() {
            return continuePrice;
        }

        void setContinuePrice(BigDecimal continuePrice) {
            this.continuePrice = continuePrice;
        }

        BigDecimal getStartingWeight() {
            return startingWeight;
        }

        void setStartingWeight(BigDecimal startingWeight) {
            this.startingWeight = startingWeight;
        }

        Boolean getUseVolume() {
            return useVolume;
        }

        void setUseVolume(Boolean useVolume) {
            this.useVolume = useVolume;
        }

        Boolean getBuyCage() {
            return buyCage;
        }

        void setBuyCage(Boolean buyCage) {
            this.buyCage = buyCage;
        }

        Boolean getExistCage() {
            return existCage;
        }

        void setExistCage(Boolean existCage) {
            this.existCage = existCage;
        }

        BigDecimal getPetWeight() {
            return petWeight;
        }

        void setPetWeight(BigDecimal petWeight) {
            this.petWeight = petWeight;
        }

        AddedWeightCage getAddedWeightCage() {
            return addedWeightCage;
        }

        void setAddedWeightCage(AddedWeightCage addedWeightCage) {
            this.addedWeightCage = addedWeightCage;
        }

        BigDecimal getVolumeCageAmount() {
            return volumeCageAmount;
        }

        void setVolumeCageAmount(BigDecimal volumeCageAmount) {
            this.volumeCageAmount = volumeCageAmount;
        }

        public BigDecimal getStartingPrice() {
            return startingPrice;
        }

        public void setStartingPrice(BigDecimal startingPrice) {
            this.startingPrice = startingPrice;
        }

        @Override
        public String toString() {
            return "TransportDTO{" +
                    "continuePrice=" + continuePrice +
                    ", startingWeight=" + startingWeight +
                    ", useVolume=" + useVolume +
                    ", buyCage=" + buyCage +
                    ", existCage=" + existCage +
                    ", petWeight=" + petWeight +
                    ", addedWeightCage=" + addedWeightCage +
                    ", volumeCageAmount=" + volumeCageAmount +
                    '}';
        }
    }

}
