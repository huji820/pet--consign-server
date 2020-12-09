package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.StationMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName StationMessageMapper
 * @Description
 * @Author LiuXiangLin
 * @Date 2019/9/10 14:01
 * @Version 1.0
 **/
@Mapper
public interface StationMessageMapper {
    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 添加一条数据
     * @Date 14:14 2019/9/10
     * @Param [stationMessage]
     **/
    int addAStationMessage(@Param("stationMessage") StationMessage stationMessage);


    /**
     * @param stationNo, lastGetTime ,state
     * @return int
     * @author LiuXiangLin
     * @description 查询管理员的所有站内信数量（包括个人信息）
     * @date 14:21 2019/10/14
     **/
    int countAdminMsgByStationNo(@Param("stationNo") int stationNo,
                                 @Param("lastGetTime") String lastGetTime,
                                 @Param("status") String status);

    /**
     * @param stationNo ,offset , limit
     * @return java.util.List<com.jxywkj.application.pet.model.common.CustomerMessage>
     * @author LiuXiangLin
     * @description 通过站点编号查询所有数据（包括个人站内信）
     * @date 14:35 2019/10/14
     **/
    List<StationMessage> listAdminMsgByStationNo(@Param("stationNo") int stationNo,
                                                 @Param("offset") int offset,
                                                 @Param("limit") int limit);
}
