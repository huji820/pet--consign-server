package com.jxywkj.application.pet.service.spring.business;

import com.jxywkj.application.pet.common.enums.BalanceFlowTypeEnum;
import com.jxywkj.application.pet.dao.business.BusinessBalanceMapper;
import com.jxywkj.application.pet.model.business.Business;
import com.jxywkj.application.pet.model.business.BusinessBalance;
import com.jxywkj.application.pet.model.consign.Order;
import com.jxywkj.application.pet.service.facade.business.BusinessBalanceFlowService;
import com.jxywkj.application.pet.service.facade.business.BusinessBalanceService;
import com.jxywkj.application.pet.service.facade.common.CustomerMessageService;
import com.yangwang.sysframework.utils.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author LiuXiangLin
 * @version 1.0
 * @className BusinessBalanceServiceImpl
 * @description
 * @date 2019/10/12 16:38
 **/
@Service
public class BusinessBalanceServiceImpl implements BusinessBalanceService {
    @Resource
    BusinessBalanceMapper businessBalanceMapper;

    @Resource
    CustomerMessageService customerMessageService;

    @Resource
    BusinessBalanceFlowService businessBalanceFlowService;

    @Override
    public int saveOrUpdate(BusinessBalance businessBalance) {
        return businessBalanceMapper.saveOrUpdateRebate(businessBalance);
    }

    @Override
    public int saveRebateAmount(Order order, Business business, BigDecimal amount) {
        // 修改余额
        businessBalanceMapper.saveOrUpdateRebate(getBusinessRebate(business, amount));

        // 站内信
        customerMessageService.saveBusinessRebate(order, business);

        // 余额流水
        businessBalanceFlowService.save(business, amount, order.getOrderNo(), null, BalanceFlowTypeEnum.CONSING_ORDER_REBATE);

        return 1;
    }

    @Override
    public BusinessBalance getByBusinessNo(String businessNo) {
        return businessBalanceMapper.getByBusinessNo(businessNo);
    }

    @Override
    public int subtract(String businessNo, BigDecimal amount) {
        return businessBalanceMapper.subtractAmount(businessNo, amount);
    }

    /**
     * @param business , rebateAmount
     * @return com.jxywkj.application.pet.model.business.BusinessBalance
     * @author LiuXiangLin
     * @description 获取返利对象
     * @date 17:41 2019/10/12
     **/
    private BusinessBalance getBusinessRebate(Business business, BigDecimal rebateAmount) {
        BusinessBalance result = new BusinessBalance();

        result.setBusinessNo(business.getBusinessNo());
        result.setLastRebateAmount(rebateAmount);
        result.setLastRebateTime(DateUtil.format(new Date(), DateUtil.FORMAT_FULL));

        return result;
    }
}
