package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.common.enums.BalanceFlowTypeEnum;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.model.consign.StationBalanceFlow;
import com.jxywkj.application.pet.model.consign.vo.StationBalanceFlowVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 站点流水
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className StationBalanceFlowService
 * @date 2019/12/28 16:29
 **/
public interface StationBalanceFlowService {
    /**
     * <p>
     * 添加一条流水(要在更新余额之后执行)
     * </p>
     *
     * @param station             站点
     * @param amount              金额
     * @param billNo              单号
     * @param linkNo              相关单号
     * @param balanceFlowTypeEnum 流水类型枚举
     * @return int
     * @author LiuXiangLin
     * @date 16:39 2019/12/28
     **/
    int save(Station station, BigDecimal amount, String billNo, String linkNo, BalanceFlowTypeEnum balanceFlowTypeEnum);

    /**
     * 查询站点的流水
     *
     * @param station
     * @param startDate
     * @param endDate
     * @return
     */
    List<StationBalanceFlowVo> listStationFlow(Station station, String startDate, String endDate, int start, int limit);

    /**
     * 站点流水查询分页数量
     *
     * @param station
     * @param startDate
     * @param endDate
     * @return
     */
    int countStationFlow(Station station, String startDate, String endDate);
}
