package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.StationWithdraw;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName StationWithdrawMapper
 * @Description 站点提现
 * @Author LiuXiangLin
 * @Date 2019/8/26 14:28
 * @Version 1.0
 **/
@Component
public interface StationWithdrawMapper {

    /**
     * @Author LiuXiangLin
     * @Description 添加一条数据
     * @Date 14:29 2019/8/26
     * @Param [stationWithdraw]
     * @return int
     **/
    int saveAStationWithdraw(@Param("stationWithdraw")StationWithdraw stationWithdraw);


    /**
     * @Author LiuXiangLin
     * @Description 更新订单状态
     * @Date 15:22 2019/8/26
     * @Param [stationWithdrawNo, state]
     * @return int
     **/
    int updateWithdrawState(@Param("stationWithdrawNo")String stationWithdrawNo,@Param("state") String state);

    /**
     * @Author LiuXiangLin
     * @Description 通过返利单号获取数据
     * @Date 15:36 2019/8/26
     * @Param [withdrawNo]
     * @return com.jxywkj.application.pet.model.consign.StationWithdraw
     **/
    StationWithdraw getByWithdrawNo(@Param("withdrawNo")String withdrawNo);

    /**
     * <p>
     * 通过站点编号查询提现流水列表
     * </p>
     *
     * @param stationNo 站点编号
     * @param offset 排除条数
     * @param limit 显示条数
     * @return java.util.List<com.jxywkj.application.pet.model.consign.StationWithdraw>
     * @author LiuXiangLin
     * @date 10:04 2019/12/6
     **/
    List<StationWithdraw> listByStationNo(@Param("stationNo")String stationNo,@Param("offset")int offset,@Param("limit") int limit);


    /**
     * 查询站点提现列表
     * @param WithdrawNo
     * @param stationNo
     * @param startDate
     * @param endDate
     * @param start
     * @param end
     * @return
     */
    List<StationWithdraw> listStationWithdraw(@Param("WithdrawNo") String WithdrawNo, @Param("stationNo") String stationNo, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("active") boolean active, @Param("start") int start, @Param("end") int end);


    /**
     * 查询站点提现列表
     * @param WithdrawNo
     * @param stationNo
     * @param startDate
     * @param endDate
     * @return
     */
    int countStationWithdraw(@Param("WithdrawNo") String WithdrawNo, @Param("stationNo") String stationNo, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("active") boolean active);

}
