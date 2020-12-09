package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.dao.consign.PartnerMapper;
import com.jxywkj.application.pet.dao.consign.TransportMapper;
import com.jxywkj.application.pet.model.consign.*;
import com.jxywkj.application.pet.model.consign.params.LonAndLat;
import com.jxywkj.application.pet.service.facade.business.AreaService;
import com.jxywkj.application.pet.service.facade.consign.ConsignOrderLedgerService;
import com.jxywkj.application.pet.service.facade.consign.StationService;
import com.jxywkj.application.pet.service.facade.consign.TransportService;
import com.yangwang.sysframework.utils.TypeConvertUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service

public class TransportServiceImpl implements TransportService {
    @Resource
    TransportMapper transportMapper;

    @Resource
    PartnerMapper partnerMapper;

    @Resource
    StationService stationService;

    @Resource
    ConsignOrderLedgerService consignOrderLedgerService;

    @Resource
    GaoDeMapUtils gaoDeMapUtils;

    @Resource
    AreaService areaService;

    @Override
    public List<Transport> listTransport(String endCity, int transportType, int stationNo, int start, int end) {
        return transportMapper.listTransport(endCity, transportType, stationNo, start, end);
    }

    @Override
    public Transport getTransport(int cityNo, String startCity, String endCity, int transportType) {
        return transportMapper.getTransport(cityNo, startCity, endCity, transportType);
    }

    @Override
    public int countTransport(String endCity, int transportType, int stationNo) {
        return transportMapper.countTransport(endCity, transportType, stationNo);
    }

    @Override
    public void updateTransport(Transport transport) {
        transportMapper.updateTransport(transport);
    }

    @Override
    public void insertTransport(Transport transport,Staff staff) {
        transport.setCityNo(areaService.getCityId(staff.getStation().getCity()));
        List<Integer> list = this.listTransportType(staff.getStation().getCity(), transport.getEndCity());
        if(list!=null){
            for(Integer transportType:list){
                //线路已存在
                if(transport.getTransportType().equals(String.valueOf(transportType))){
                    return;
                }
            }
        }
        transportMapper.insertTransport(transport);
    }

    @Override
    public void insertAddress(Transport transport, Staff staff, String province) throws Exception {
        //transport.setStation(staff.getStation());
        //transport.setCityNo(staff.getStation().getCity());
        transport.setCityNo(areaService.getCityId(staff.getStation().getCity()));
        /*
         * 拼接字符串为运输线路名称
         * */
        transport.setTransportName(staff.getStation().getCity() + '-' + transport.getEndCity());
        /*从staff获取起始城市和起始区/县*/
        transport.setStartCity(staff.getStation().getCity());
        transport.setStartRegion(staff.getStation().getDistrict());

        Partner partner = new Partner();
        /*在添加之前从transport获取确定后的省市区*/
        partner.setPartnerName(transport.getEndCity());
        partner.setCity(transport.getEndCity());
        partner.setCounty(transport.getEndRegion());
        partner.setProvince(province);
        /*从staff获取员工编号*/
        partner.setStation(staff.getStation());
        LonAndLat lonAndLat = gaoDeMapUtils.getLonAndLat(partner.getProvince() + partner.getCity() + partner.getCounty());

        /*通过省市区获取经纬度*/
        partner.setLat(Double.valueOf(lonAndLat.getLatitude()));
        partner.setLng(Double.valueOf(lonAndLat.getLongitude()));

        partnerMapper.insertPartnerAddress(partner);
        /*添加只后返回Partner表单id,*/
        transport.setPartner(partner);
        transportMapper.insertTransport(transport);
    }


    @Override
    public void deleteTransport(int transport) {
        transportMapper.deleteTransport(transport);
    }

    @Override
    public List<String> listStartCity() {
//        return transportMapper.listStartCity();
        // 改为查询可用站点的城市
        return stationService.listStartCity();
    }

    @Override
    public String getStartCityByTransportNo(int transportNo) {
        return transportMapper.getStartCityByTransportNo(transportNo);
    }

    @Override
    public List<Transport> listByCityAndType(String startCity, String endCity, int transportType) {
        Transport transport = new Transport();
        transport.setStartCity(startCity);
        transport.setEndCity(endCity);
        transport.setTransportType(String.valueOf(transportType));

        return transportMapper.listTransportByCondition(transport);
    }

    @Override
    public List<Integer> listAllTransportNoByStationNo(int stationNo) {
        return transportMapper.listAllTransportNoByStationNo(stationNo);
    }

    @Override
    public List<Integer> listByStationNoAndTransportType(int stationNo, int... transportType) {
        return transportMapper.listByStationNoAndTransportType(stationNo, transportType);
    }

    @Override
    public int updateTransportPrice(Transport transport) {
        return transportMapper.updateTransportMaxWeight(transport.getTransportNo(), transport.getMaxWeight());
    }

    @Override
    public int updateTransportStartWeight(Transport transport) {
        return transportMapper.updateStartWeight(transport.getTransportNo(), transport.getStartingWeight());
    }

    @Override
    public List<Transport> listByInstance(Station station, BigDecimal instance) {
        Station existsStation = stationService.getStation(station.getStationNo());
        return transportMapper.listByInstance(existsStation.getLng(), existsStation.getLat(), instance);
    }

    @Override
    public Transport getTransportByOrderNo(String orderNo) {
        //获取订单账单
        OrderLedger orderLedger = consignOrderLedgerService.getByOrderNo(orderNo);
        //获取站点
        Station station = stationService.getStation(orderLedger.getStation().getStationNo());
        //开始城市
        String startCity = orderLedger.getStartCity()==null?station.getCity():orderLedger.getStartCity();
        //结束城市
        String endCity = orderLedger.getEndCity();
        //运输方式
        int transportType = orderLedger.getTransportType();

        return Transport.defaultTransport(startCity,endCity, TypeConvertUtil.$Str(transportType));
    }


    @Override
    public List<String> listEndCity(String startCity, int transportNo) {
        return transportMapper.listEndCity(startCity, transportNo);
    }

    @Override
    public List<Integer> listTransportType(String startCity, String endCity) {
        return transportMapper.listTransportType(startCity, endCity);
    }

    @Override
    public List<Transport> listTransportByCondition(Transport transport) {
        return transportMapper.listTransportByCondition(transport);
    }

    @Override
    public Transport getTransportByTransportNo(int transportNo) {
        return transportMapper.getTransportByTransportNo(transportNo);
    }

    @Override
    public void deleteTransportByPartnerNo(int partnerNo) {
        transportMapper.deleteTransportByPartnerNo(partnerNo);
    }
}
