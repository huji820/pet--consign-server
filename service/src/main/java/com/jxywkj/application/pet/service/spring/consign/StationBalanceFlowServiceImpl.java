package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.common.enums.BalanceFlowTypeEnum;
import com.jxywkj.application.pet.dao.consign.StationBalanceFlowMapper;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.model.consign.StationBalanceFlow;
import com.jxywkj.application.pet.model.consign.vo.StationBalanceFlowVo;
import com.jxywkj.application.pet.service.facade.consign.StationBalanceFlowService;
import com.jxywkj.application.pet.service.facade.consign.StationBalanceService;
import com.yangwang.sysframework.utils.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className StationBalanceFlowServiceImpl
 * @date 2019/12/28 16:39
 **/
@Service
public class StationBalanceFlowServiceImpl implements StationBalanceFlowService {
    @Resource
    StationBalanceService stationBalanceService;

    @Resource
    StationBalanceFlowMapper stationBalanceFlowMapper;

    @Override
    public int save(Station station, BigDecimal amount,String billNo, String linkNo, BalanceFlowTypeEnum balanceFlowTypeEnum) {
        // 获取站点余额
        BigDecimal stationBalance = stationBalanceService.getTotalByStationNo(station.getStationNo());

        StationBalanceFlow stationBalanceFlow = new StationBalanceFlow();
        stationBalanceFlow.setBalance(stationBalance);
        stationBalanceFlow.setDateTime(DateUtil.formatFull(new Date()));
        stationBalanceFlow.setFlowType(balanceFlowTypeEnum.getType());
        stationBalanceFlow.setStation(station);
        stationBalanceFlow.setLinkNo(linkNo);
        stationBalanceFlow.setBillNo(billNo);
        stationBalanceFlow.setFlowAmount(amount);

        return stationBalanceFlowMapper.save(stationBalanceFlow);
    }

    @Override
    public List<StationBalanceFlowVo> listStationFlow(Station station, String startDate, String endDate, int start, int limit) {
        return stationBalanceFlowMapper.listStationFlow(station, startDate, endDate, start, limit);
    }

    @Override
    public int countStationFlow(Station station, String startDate, String endDate) {
        return stationBalanceFlowMapper.countStationFlow(station, startDate, endDate);
    }
}
