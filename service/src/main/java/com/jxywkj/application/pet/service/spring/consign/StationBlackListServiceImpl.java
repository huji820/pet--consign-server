package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.dao.consign.StationBlackListMapper;
import com.jxywkj.application.pet.model.consign.StationBlacklist;
import com.jxywkj.application.pet.service.facade.consign.StationBlackListService;
import com.yangwang.sysframework.utils.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className StationBlackListServiceImpl
 * @date 2019/11/23 10:50
 **/
@Service
public class StationBlackListServiceImpl implements StationBlackListService {
    @Resource
    StationBlackListMapper stationBlackListMapper;


    @Override
    public int saveOrUpdate(StationBlacklist stationBlacklist) {
        stationBlacklist.setCreateTime(DateUtil.formatFull(new Date()));
        return stationBlackListMapper.saveOrUpdate(stationBlacklist);
    }

    @Override
    public int deleteByBlackStationNo(int stationNo, int blackStationNo) {
        return stationBlackListMapper.deleteByBlackStationNo(stationNo, blackStationNo);
    }

    @Override
    public int deleteByStationNo(int stationNo) {
        return stationBlackListMapper.deleteByStationNo(stationNo);
    }

    @Override
    public StationBlacklist getByBlackStationNo(int stationNo, int checkStationNo) {
        return stationBlackListMapper.getByBlackStationNo(stationNo, checkStationNo);
    }

    @Override
    public StationBlacklist listByStationNo(int stationNo) {
        return stationBlackListMapper.listByStationNo(stationNo);
    }
}
