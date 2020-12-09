package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.common.enums.RebateBufferEnum;
import com.jxywkj.application.pet.dao.consign.StationBalanceBufferMapper;
import com.jxywkj.application.pet.model.consign.StationBalanceBuffer;
import com.jxywkj.application.pet.model.consign.StationWithdraw;
import com.jxywkj.application.pet.service.facade.consign.StationBalanceBufferService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className StationBalanceBufferServiceImpl
 * @date 2019/12/6 17:32
 **/
@Service
public class StationBalanceBufferServiceImpl implements StationBalanceBufferService {
    @Resource
    StationBalanceBufferMapper stationBalanceBufferMapper;

    @Override
    public int saveWithdraw(StationWithdraw stationWithdraw) {
        if (stationWithdraw == null) {
            return 0;
        }

        StationBalanceBuffer stationBalanceBuffer = new StationBalanceBuffer();
        stationBalanceBuffer.setAmount(stationWithdraw.getAmount());
        stationBalanceBuffer.setBillType(RebateBufferEnum.STATION_WITHDRAW.getBillType());
        stationBalanceBuffer.setBillNo(stationWithdraw.getWithdrawNo());
        stationBalanceBuffer.setStation(stationWithdraw.getStation());

        return stationBalanceBufferMapper.save(stationBalanceBuffer);
    }

    @Override
    public int deleteByWithdrawNo(String withdrawNo) {
        return stationBalanceBufferMapper.delete(withdrawNo);
    }

    @Override
    public BigDecimal getTotalAmount(String stationNo) {
        return stationBalanceBufferMapper.getTotalAmount(stationNo);
    }
}
