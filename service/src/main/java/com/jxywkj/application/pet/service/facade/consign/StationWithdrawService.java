package com.jxywkj.application.pet.service.facade.consign;


import com.jxywkj.application.pet.model.consign.StationWithdraw;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName StationWithdrawService
 * @Description 站点提现
 * @Author LiuXiangLin
 * @Date 2019/8/26 14:59
 * @Version 1.0
 **/
public interface StationWithdrawService {

    /**
     * <p>
     * 新增提现
     * </p>
     *
     * @param customerNo 用户编号
     * @param withdrawAmount 提现金额
     * @return int
     * @author LiuXiangLin
     * @date 10:37 2020/3/5
     **/
    int saveStationWithdraw(String customerNo, BigDecimal withdrawAmount);

    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 确认提现
     * @Date 15:20 2019/8/26
     * @Param [stationWithdrawNo]
     **/
    int confirmWithdraw(String stationWithdrawNo);

    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 确认提现
     * @Date 15:20 2019/8/26
     * @Param stationWithdraws
     **/
    int confirmWithdraws(List<StationWithdraw> stationWithdraws);

    /**
     * <p>
     * 查询站点提现申请流水
     * </p>
     *
     * @param stationNo 站点编号
     * @param offset    排除条数
     * @param limit     显示条数
     * @return java.util.List<com.jxywkj.application.pet.model.consign.StationWithdraw>
     * @author LiuXiangLin
     * @date 10:02 2019/12/6
     **/
    List<StationWithdraw> listByStationNo(String stationNo, int offset, int limit);

    /**
     * <p>
     * 驳回提现申请
     * </p>
     *
     * @param stationWithdrawNo 提现单号
     * @return int
     * @author LiuXiangLin
     * @date 17:54 2019/12/6
     **/
    int rejectWithdraw(String stationWithdrawNo);

    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 确认提现
     * @Date 15:20 2019/8/26
     * @Param stationWithdraws
     **/
    int rejectWithdraws(List<StationWithdraw> stationWithdraws);

    /**
     * 查询站点提现列表
     * @param withdrawNo
     * @param stationNo
     * @param startDate
     * @param endDate
     * @param start
     * @param end
     * @return
     */
    List<StationWithdraw> listStationWithdraw(String withdrawNo, String stationNo, String startDate, String endDate, boolean active, int start,int end);


    /**
     * 查询站点提现列表
     * @param withdrawNo
     * @param stationNo
     * @param startDate
     * @param endDate
     * @return
     */
    int countStationWithdraw(String withdrawNo, String stationNo, String startDate, String endDate, boolean active);

}
