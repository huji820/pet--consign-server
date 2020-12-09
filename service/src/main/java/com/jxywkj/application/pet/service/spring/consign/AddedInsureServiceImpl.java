package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.common.utils.CollectionUtils;
import com.jxywkj.application.pet.dao.consign.AddedInsureMapper;
import com.jxywkj.application.pet.model.consign.AddedInsure;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.model.consign.Transport;
import com.jxywkj.application.pet.service.facade.consign.AddedInsureService;
import com.jxywkj.application.pet.service.facade.consign.TransportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName AddedInsureServiceImpl
 * @Author LiuXiangLin
 * @Date 2019/7/25 14:34
 * @Version 1.0
 **/
@Service
public class AddedInsureServiceImpl implements AddedInsureService {
    @Resource
    AddedInsureMapper addedInsureMapper;

    @Resource
    TransportService transportService;

    private Logger logger = LoggerFactory.getLogger(AddedInsureServiceImpl.class);

    @Override
    public AddedInsure getByStationNo(int stationNo) {
        return addedInsureMapper.getByStationNo(stationNo);
    }

    @Override
    public AddedInsure getByStartCityAndEndCity(String startCity, String endCity, Station station) {
        /*获取运运输路线*/
        Transport transportParam = new Transport();
        transportParam.setStartCity(startCity);
        transportParam.setEndCity(endCity);

        List<Transport> transportList = transportService.listTransportByCondition(transportParam);

        if (CollectionUtils.isEmpty(transportList)) {
            logger.error("获取运输路线列表失败");
            throw new RuntimeException("获取运输路线列表失败！");

        }

        /*获取费率*/
        return addedInsureMapper.getByStationNo(station.getStationNo());
    }

    @Override
    public int update(AddedInsure addedInsure) {
        return addedInsureMapper.update(addedInsure);
    }
}
