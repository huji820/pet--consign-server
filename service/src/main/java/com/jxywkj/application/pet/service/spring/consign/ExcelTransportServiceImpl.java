package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.dao.consign.ExcelTransportMapper;
import com.jxywkj.application.pet.model.common.ExcelTransportDO;
import com.jxywkj.application.pet.service.facade.consign.ExcelTransportService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName ExcelTransprotServiceImpl
 * @Description 运输路线Service
 * @Author LiuXiangLin
 * @Date 2019/7/23 11:54
 * @Version 1.0
 **/
@Service
public class ExcelTransportServiceImpl implements ExcelTransportService {

    @Resource
    ExcelTransportMapper excelTransportMapper;

    @Override
    public List<ExcelTransportDO> listByStationNo(String stationNo) {
        return excelTransportMapper.listTranSportByStationNo(stationNo);
    }
}
