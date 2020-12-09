package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.common.utils.CollectionUtils;
import com.jxywkj.application.pet.dao.consign.AddedReceiptMapper;
import com.jxywkj.application.pet.model.consign.AddedReceipt;
import com.jxywkj.application.pet.service.facade.consign.AddedReceiptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName AddedReceiptServiceImpl
 * @Author LiuXiangLin
 * @Date 2019/7/25 14:37
 * @Version 1.0
 **/
@Service
public class AddedReceiptServiceImpl implements AddedReceiptService {

    @Resource
    AddedReceiptMapper addedReceiptMapper;

    @Override
    public int saveOrUpdateList(List<AddedReceipt> addedReceiptList) {
        if (!CollectionUtils.isEmpty(addedReceiptList)) {
            addedReceiptMapper.saveOrUpdateList(addedReceiptList);
        }
        return 0;
    }

    @Override
    public List<AddedReceipt> listByStationNo(int stationNo) {
        return addedReceiptMapper.listByStationNo(stationNo);
    }
}
