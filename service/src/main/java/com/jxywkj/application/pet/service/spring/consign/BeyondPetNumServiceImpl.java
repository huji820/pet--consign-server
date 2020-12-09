package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.dao.consign.BeyondPetNumMapper;
import com.jxywkj.application.pet.model.consign.BeyondPetNum;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.service.facade.consign.BeyondPetNumService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className BeyondPetNumServiceImpl
 * @date 2019/11/22 14:58
 * @see com.jxywkj.application.pet.service.facade.consign.BeyondPetNumService
 **/
@Service
public class BeyondPetNumServiceImpl implements BeyondPetNumService {
    @Resource
    BeyondPetNumMapper beyondPetNumMapper;

    @Override
    public BeyondPetNum getByStationNoAndType(int stationNo, int type) {
        return beyondPetNumMapper.getByStationNoAndType(stationNo, type);
    }

    @Override
    public List<BeyondPetNum> listByStationNo(int stationNo) {
        return beyondPetNumMapper.listByStationNo(stationNo);
    }

    @Override
    public BeyondPetNum getByTransportNo(Station station, int transportNo) {
        return beyondPetNumMapper.getByTransportNo(station, transportNo);
    }

    @Override
    public int saveOrUpdate(BeyondPetNum beyondPetNum) {
        return beyondPetNumMapper.saveOrUpdate(beyondPetNum);
    }

    @Override
    public int saveOrUpdateByStation(List<BeyondPetNum> beyondPetNumList) {
        return beyondPetNumMapper.saveOrUpdateByStation(beyondPetNumList);
    }

    @Override
    public int deleteByStationNo(int stationNo) {
        return beyondPetNumMapper.deleteByStationNo(stationNo);
    }
}
