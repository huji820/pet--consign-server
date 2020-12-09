package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.common.enums.BusinessStateEnum;
import com.jxywkj.application.pet.common.enums.StaffStateEnum;
import com.jxywkj.application.pet.dao.consign.BalanceMapper;
import com.jxywkj.application.pet.model.business.Business;
import com.jxywkj.application.pet.model.business.BusinessBalance;
import com.jxywkj.application.pet.model.common.Customer;
import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.service.facade.business.BusinessBalanceBufferService;
import com.jxywkj.application.pet.service.facade.business.BusinessBalanceService;
import com.jxywkj.application.pet.service.facade.business.BusinessService;
import com.jxywkj.application.pet.service.facade.common.CustomerService;
import com.jxywkj.application.pet.service.facade.consign.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @ClassName BalanceServiceImpl
 * @Description
 * @Author LiuXiangLin
 * @Date 2019/8/20 9:45
 * @Version 1.0
 **/
@Service
public class BalanceServiceImpl implements BalanceService {
    @Resource
    BalanceMapper balanceMapper;

    @Resource
    CustomerService customerService;

    @Resource
    StaffService staffService;

    @Resource
    StationService stationService;

    @Resource
    StationBalanceService stationBalanceService;

    @Resource
    BusinessService businessService;

    @Resource
    BusinessBalanceService businessBalanceService;

    @Resource
    BusinessBalanceBufferService businessBalanceBufferService;

    @Resource
    StationBalanceBufferService stationBalanceBufferService;

    @Override
    public BigDecimal getByCustomerNo(String customerNo) {
        BigDecimal result = BigDecimal.valueOf(0);

        // 个人余额
        Customer customer = customerService.getCustomerByCustomerNo(customerNo);
        if (customer == null) {
            return BigDecimal.ZERO;
        }
        result = result.add(balanceMapper.getByCustomerNo(customer.getCustomerNo()));

        // 站点余额
        Station station = stationService.getByCustomerNo(customerNo);
        if (station != null) {
            // 站点当时余额
            result = result.add(stationBalanceService.getTotalByStationNo(station.getStationNo()));
            // 临时减扣的数据
            result = result.add(stationBalanceBufferService.getTotalAmount(String.valueOf(station.getStationNo())));
        }

        // 商家余额
        Business business = businessService.getBusinessByPhone(customer.getPhone(), BusinessStateEnum.NORMAL);
        if (business != null) {
            BusinessBalance businessBalance = businessBalanceService.getByBusinessNo(business.getBusinessNo());
            if (businessBalance != null) {
                result = result.add(businessBalance.getTotalAmount());
                // 商家临时
                result = result.add(businessBalanceBufferService.getTotalAmount(business.getBusinessNo()));
            }
        }


        return result;
    }
}
