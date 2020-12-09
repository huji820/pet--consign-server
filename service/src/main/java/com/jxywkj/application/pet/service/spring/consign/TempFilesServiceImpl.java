package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.dao.consign.TempFilesMapper;
import com.jxywkj.application.pet.model.consign.file.OrderStateTempFiles;
import com.jxywkj.application.pet.service.facade.consign.ConsignOrderService;
import com.jxywkj.application.pet.service.facade.consign.TempFilesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName TempFilesServiceImpl
 * @Description
 * @Author LiuXiangLin
 * @Date 2019/8/13 11:16
 * @Version 1.0
 **/
@Service
public class TempFilesServiceImpl implements TempFilesService {
    @Resource
    TempFilesMapper tempFilesMapper;

    @Resource
    ConsignOrderService consignOrderService;

    @Override
    public int addATempFiles(OrderStateTempFiles orderStateTempFiles) {
        return tempFilesMapper.addATempFiles(orderStateTempFiles);
    }

    @Override
    public List<OrderStateTempFiles> listByOrderNo(String orderNo) {
        return tempFilesMapper.listByOrderNo(orderNo);
    }

    @Override
    public int deleteByOrderNo(String orderNo) {
        return tempFilesMapper.deleteByOrderNo(orderNo);
    }
}
