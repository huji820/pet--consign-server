package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.common.enums.PetCageStateEnum;
import com.jxywkj.application.pet.common.enums.TransportTypeEnum;
import com.jxywkj.application.pet.common.utils.CollectionUtils;
import com.jxywkj.application.pet.dao.consign.AddedWeightCageMapper;
import com.jxywkj.application.pet.model.consign.AddedWeightCage;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.service.facade.consign.AddedWeightCageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author LiuXiangLin
 * @version 1.0
 * @className AddedWeightCageServiceImpl
 * @date 2019/10/19 14:36
 **/
@Service
public class AddedWeightCageServiceImpl implements AddedWeightCageService {
    @Resource
    AddedWeightCageMapper addedWeightCageMapper;

    @Override
    public int saveOrUpdateList(List<AddedWeightCage> addedWeightCageList, int stationNo) {
        // 空判断
        if (CollectionUtils.isEmpty(addedWeightCageList)) {
            return 0;
        }
        // 设置站点信息
        Station station = new Station(stationNo);
        for (AddedWeightCage addedWeightCage : addedWeightCageList) {
            addedWeightCage.setStation(station);
            addedWeightCage.setUseWeight(PetCageStateEnum.USABLE.getState());
        }
        // 删除数据
        addedWeightCageMapper.deleteByStationNoAndType(stationNo, addedWeightCageList.get(0).getTransportType());
        // 保存数据
        return addedWeightCageMapper.saveList(addedWeightCageList);
    }

    @Override
    public List<AddedWeightCage> listByStationNoAndTransportType(int stationNo, int transportType) {
        return addedWeightCageMapper.listByStationNoAndType(stationNo, transportType);
    }
}
