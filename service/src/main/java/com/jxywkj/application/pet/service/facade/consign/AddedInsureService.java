package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.AddedInsure;
import com.jxywkj.application.pet.model.consign.Station;

/**
 * @ClassName AddedInsureService
 * @Description 增值服务_保价
 * @Author LiuXiangLin
 * @Date 2019/7/25 14:33
 * @Version 1.0
 **/
public interface AddedInsureService {
    /**
     * @Author LiuXiangLin
     * @Description 通过站点编号查询数据
     * @Date 14:34 2019/7/25
     * @Param [stationNo]
     * @return com.jxywkj.application.pet.model.consign.AddedInsure
     **/
    AddedInsure getByStationNo(int stationNo);


    /**
     * @Author LiuXiangLin
     * @Description 通过开始城市目标城市获取保价配置
     * @Date 15:35 2019/7/25
     * @Param [startCity, endCity]
     * @return com.jxywkj.application.pet.model.consign.AddedInsure
     **/
    AddedInsure getByStartCityAndEndCity(String startCity, String endCity, Station station);


    /**
     * @Author LiuXiangLin
     * @Description 更新数据
     * @Date 17:27 2019/8/5
     * @Param [addedInsure]
     * @return int
     **/
    int update(AddedInsure addedInsure);

}
