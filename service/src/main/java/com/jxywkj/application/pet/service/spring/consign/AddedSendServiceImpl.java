package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.common.utils.CollectionUtils;
import com.jxywkj.application.pet.dao.consign.AddedSendMapper;
import com.jxywkj.application.pet.model.consign.AddedSend;
import com.jxywkj.application.pet.service.facade.consign.AddedSendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName AddedSendServiceImpl
 * @Author LiuXiangLin
 * @Date 2019/7/25 14:44
 * @Version 1.0
 **/
@Service
public class AddedSendServiceImpl implements AddedSendService {
    @Resource
    AddedSendMapper addedSendMapper;

    @Override
    public int saveOrUpdateList(List<AddedSend> addedSendList) {
        if (!CollectionUtils.isEmpty(addedSendList)) {
            return addedSendMapper.saveOrUpdateList(addedSendList);
        }
        return 0;
    }

    @Override
    public List<AddedSend> listByStationNo(int stationNo) {
        return addedSendMapper.listByStationNo(stationNo);
    }
}
