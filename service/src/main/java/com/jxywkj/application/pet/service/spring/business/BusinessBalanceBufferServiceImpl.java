package com.jxywkj.application.pet.service.spring.business;

import com.jxywkj.application.pet.common.enums.RebateBufferEnum;
import com.jxywkj.application.pet.dao.business.BusinessBalanceBufferMapper;
import com.jxywkj.application.pet.model.business.BusinessBalanceBuffer;
import com.jxywkj.application.pet.model.business.BusinessWithdraw;
import com.jxywkj.application.pet.service.facade.business.BusinessBalanceBufferService;
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
 * @className BusinessBalanceBufferServiceImpl
 * @date 2019/12/6 16:52
 **/
@Service
public class BusinessBalanceBufferServiceImpl implements BusinessBalanceBufferService {
    @Resource
    BusinessBalanceBufferMapper businessBalanceBufferMapper;


    @Override
    public int saveWithdraw(BusinessWithdraw businessWithdraw) {
        if (businessWithdraw == null) {
            return 0;
        }

        BusinessBalanceBuffer businessBalanceBuffer = new BusinessBalanceBuffer();
        businessBalanceBuffer.setAmount(businessWithdraw.getAmount());
        businessBalanceBuffer.setBillNo(businessWithdraw.getWithdrawNo());
        businessBalanceBuffer.setBusiness(businessWithdraw.getBusiness());
        businessBalanceBuffer.setBillType(RebateBufferEnum.BUSINESS_WITHDRAW.getBillType());

        return businessBalanceBufferMapper.save(businessBalanceBuffer);
    }

    @Override
    public BigDecimal getTotalAmount(String businessNo) {
        return businessBalanceBufferMapper.getTotalByBusinessNo(businessNo);
    }

    @Override
    public int deleteByWithdrawNo(String withdrawNo) {
        return businessBalanceBufferMapper.delete(withdrawNo);
    }
}
