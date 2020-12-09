package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.common.ExcelTransportDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName ExcelTransportMapper
 * @Description Excel运输路线Mapper接口
 * @Author LiuXiangLin
 * @Date 2019/7/23 11:46
 * @Version 1.0
 **/
public interface ExcelTransportMapper {
    /**
     * @Author LiuXiangLin
     * @Description 通过站点No查询运输路线
     * @Date 11:50 2019/7/23
     * @Param [stationNo]
     * @return java.util.List<com.jxywkj.application.pet.model.common.ExcelTransportDO>
     **/
    List<ExcelTransportDO> listTranSportByStationNo(@Param("stationNo") String stationNo);
}
