package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.dao.consign.TransportTakeDetailMapper;
import com.jxywkj.application.pet.model.consign.TransportTakeDetail;
import com.jxywkj.application.pet.model.consign.params.LonAndLat;
import com.jxywkj.application.pet.service.facade.consign.TransportTakeDetailService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className TransportTakeDetailServiceImpl
 * @date 2020/4/21 10:01
 **/
@Service
public class TransportTakeDetailServiceImpl implements TransportTakeDetailService {
    @Resource
    TransportTakeDetailMapper transportTakeDetailMapper;

    @Resource
    GaoDeMapUtils gaoDeMapUtils;

    /**
     * 二字码长度
     */
    private static final int CODE_LENGTH = 2;

    @Override
    public int saveOrUpdate(TransportTakeDetail transportTakeDetail) throws Exception {
        LonAndLat lonAndLat = gaoDeMapUtils.getLonAndLat(transportTakeDetail.getProvince() + transportTakeDetail.getCity() + transportTakeDetail.getRegion() + transportTakeDetail.getDetailAddress());
        transportTakeDetail.setLatitude(Double.valueOf(lonAndLat.getLatitude()));
        transportTakeDetail.setLongitude(Double.valueOf(lonAndLat.getLongitude()));
        transportTakeDetail.setCode(subCode(transportTakeDetail.getCode()));
        return transportTakeDetailMapper.saveOrUpdate(transportTakeDetail);
    }

    @Override
    public List<TransportTakeDetail> listByTransportNoAndCode(int transportNo, String code) {
        return transportTakeDetailMapper.listByTransportNoAndCode(transportNo, subCode(code));
    }

    @Override
    public List<TransportTakeDetail> listByOrderNo(String orderNo) {
        return transportTakeDetailMapper.listByOrderNo(orderNo);
    }


    /**
     * <p>
     * 截取code
     * </p>
     *
     * @param code 航班或者二字码
     * @return java.lang.String
     * @author LiuXiangLin
     * @date 9:14 2020/6/1
     **/
    public static String subCode(String code) {
        if (StringUtils.isNotBlank(code) && code.length() > CODE_LENGTH) {
            code = code.substring(0, CODE_LENGTH).toUpperCase();
        }
        return code;
    }
}
