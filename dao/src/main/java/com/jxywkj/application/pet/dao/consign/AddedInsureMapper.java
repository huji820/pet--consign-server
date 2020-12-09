package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.AddedInsure;
import org.apache.ibatis.annotations.Param;


/**
 * @ClassName AddedInsureMapper
 * @Description 增值服务_保价
 * @Author LiuXiangLin
 * @Date 2019/7/25 14:15
 * @Version 1.0
 **/
public interface AddedInsureMapper {

    /**
     * @Author LiuXiangLin
     * @Description 通过站点编号获取所有的保价服务
     * @Date 14:17 2019/7/25
     * @Param [stationNo]
     * @return java.util.List<com.jxywkj.application.pet.model.consign.AddedInsure>
     **/
    AddedInsure getByStationNo(@Param("stationNo") int stationNo);

    /**
     * @Author LiuXiangLin
     * @Description 更新数据
     * @Date 17:27 2019/8/5
     * @Param [addedInsure]
     * @return int
     **/
    int update(@Param("addedInsure") AddedInsure addedInsure);
}
