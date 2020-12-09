package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.AddedWeightCage;
import com.jxywkj.application.pet.model.consign.Transport;

import java.math.BigDecimal;
import java.util.List;

/**
 * 按重量笼具配置
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className AddedWeightCageService
 * @date 2019/10/19 14:31
 **/
public interface AddedWeightCageService {
    /**
     * 保存一个类型梯度
     *
     * @param addedWeightCageList 笼具列表
     * @param stationNo           站点编号
     * @return int
     * @author LiuXiangLin
     * @date 14:34 2019/10/19
     **/
    int saveOrUpdateList(List<AddedWeightCage> addedWeightCageList, int stationNo);

    /**
     * 通过站点编号以及运输类型查询数据
     *
     * @param stationNo     站点编号
     * @param transportType 运输类型
     * @return java.util.List<com.jxywkj.application.pet.model.consign.AddedWeightCage>
     * @author LiuXiangLin
     * @date 17:45 2019/10/19
     **/
    List<AddedWeightCage> listByStationNoAndTransportType(int stationNo, int transportType);
}
