package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.common.enums.TransportTypeEnum;
import com.jxywkj.application.pet.dao.consign.PositionMapper;
import com.jxywkj.application.pet.model.consign.Position;
import com.jxywkj.application.pet.service.facade.consign.PositionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName PositionServiceImpl
 * @Author LiuXiangLin
 * @Date 2019/7/26 11:32
 * @Version 1.0
 **/
@Service
public class PositionServiceImpl implements PositionService {
    @Resource
    PositionMapper positionMapper;

    @Override
    public int saveOrUpdate(Position position) {
        // 与机场有关的运输类型由单飞和随机
        if (TransportTypeEnum.RANDOM.getType() == position.getType() || TransportTypeEnum.AIRCRAFT.getType() == position.getType()) {
            Position other = position.copy(position);
            other.setType(TransportTypeEnum.RANDOM.getType() == position.getType() ? TransportTypeEnum.AIRCRAFT.getType() : TransportTypeEnum.RANDOM.getType());
            positionMapper.saveOrUpdate(other);
        }
        return positionMapper.saveOrUpdate(position);
    }

    @Override
    public Position getByStationNoAndType(int stationNo, int type) {
        return positionMapper.getByStationNoAndType(stationNo, type);
    }
}
