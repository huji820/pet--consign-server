package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.common.ExcelTransportDO;

import java.util.List;

/**
 * @ClassName ExcelTransportService
 * @Description Excel运输路线Service层
 * @Author LiuXiangLin
 * @Date 2019/7/23 11:52
 * @Version 1.0
 **/
public interface ExcelTransportService {
    /**
     * @Author LiuXiangLin
     * @Description 通过StationNo查询数据
     * @Date 11:54 2019/7/23
     * @Param [String]
     * @return java.util.List<com.jxywkj.application.pet.model.common.ExcelTransportDO>
     **/
    List<ExcelTransportDO> listByStationNo(String stationNo);

}
