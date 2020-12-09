package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.model.consign.StationBalanceFlow;
import com.jxywkj.application.pet.model.consign.vo.StationBalanceFlowVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 站点余额总流水
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className StationBalanceFlowMapper
 * @date 2019/12/28 16:00
 **/
@Mapper
public interface StationBalanceFlowMapper {
    /**
     * <p>
     * 新增一条数据
     * </p>
     *
     * @param stationBalanceFlow 站点流水兑现
     * @return int
     * @author LiuXiangLin
     * @date 16:01 2019/12/28
     **/
    int save(@Param("stationBalanceFlow") StationBalanceFlow stationBalanceFlow);

    /**
     * 查询站点的流水
     * @param station
     * @param startDate
     * @param endDate
     * @return
     */
    List<StationBalanceFlowVo> listStationFlow(@Param("station") Station station, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("start") int start, @Param("limit") int limit);

    /**
     * 站点流水查询分页数量
     * @param station
     * @param startDate
     * @param endDate
     * @return
     */
    int countStationFlow(@Param("station") Station station, @Param("startDate") String startDate, @Param("endDate") String endDate);
}
