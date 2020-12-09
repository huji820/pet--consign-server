package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.dao.consign.PartnerMapper;
import com.jxywkj.application.pet.model.consign.Partner;
import com.jxywkj.application.pet.service.facade.consign.PartnerService;
import com.jxywkj.application.pet.service.facade.consign.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description
 * @Author Administrator
 * @Date 2019-06-23 18:47
 * @Version 1.0
 */
@Service
public class PartnerServiceImpl implements PartnerService {

    @Autowired
    PartnerMapper partnerMapper;


    @Autowired
    TransportService transportService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertPartner(Partner partner) {
        partnerMapper.insertPartner(partner);
    }

    @Override
    public void deletePartner(int partnerNo) {
        partnerMapper.deletePartner(partnerNo);
        transportService.deleteTransportByPartnerNo(partnerNo);
    }

    @Override
    public void updatePartner(Partner partner) {
        partnerMapper.updatePartner(partner);

    }

    @Override
    public int countPartner(int stationNo, String partnerName, String province, String city) {
        return partnerMapper.countPartner(stationNo, partnerName, province, city);
    }

    @Override
    public List<Partner> listPartner(int stationNo, String partnerName, String province, String city, String county, int start, int end) {
        return partnerMapper.listPartner(stationNo, partnerName, province, city, county, start, end);
    }
}
