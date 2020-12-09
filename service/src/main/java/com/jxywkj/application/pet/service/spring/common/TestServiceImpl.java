package com.jxywkj.application.pet.service.spring.common;

import com.jxywkj.application.pet.common.utils.CollectionUtils;
import com.jxywkj.application.pet.dao.common.TestMapper;
import com.jxywkj.application.pet.model.consign.OrderPremium;
import com.jxywkj.application.pet.service.facade.common.TestService;
import com.jxywkj.application.pet.service.facade.consign.StationBalanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className TestServiceImpl
 * @date 2020/5/8 9:23
 **/
@Service
public class TestServiceImpl implements TestService {
    @Resource
    TestMapper testMapper;

    @Resource
    StationBalanceService stationBalanceService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int payOrderPremium() {
        List<OrderPremium> orderPremiumList = testMapper.listUnpaid();
        if (!CollectionUtils.isEmpty(orderPremiumList)) {
            for (OrderPremium orderPremium : orderPremiumList) {
                stationBalanceService.saveOrderPremium(orderPremium);
            }
        }

        return 0;
    }
}
