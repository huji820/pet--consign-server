package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.common.enums.AppTypeEnum;
import com.jxywkj.application.pet.common.enums.StaffStateEnum;
import com.jxywkj.application.pet.dao.consign.CustomerCouponMapper;
import com.jxywkj.application.pet.model.common.CustomerOpenId;
import com.jxywkj.application.pet.model.consign.Coupon;
import com.jxywkj.application.pet.model.consign.Order;
import com.jxywkj.application.pet.service.facade.business.BusinessService;
import com.jxywkj.application.pet.service.facade.common.CustomerOpenIdService;
import com.jxywkj.application.pet.service.facade.consign.AddresseeCouponService;
import com.jxywkj.application.pet.service.facade.consign.ConsignOrderService;
import com.jxywkj.application.pet.service.facade.consign.CustomerCouponService;
import com.jxywkj.application.pet.service.facade.consign.StaffService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName CustomerCouponServiceImpl
 * @Description
 * @Author LiuXiangLin
 * @Date 2019/8/20 14:22
 * @Version 1.0
 **/
@Service
public class CustomerCouponServiceImpl implements CustomerCouponService {
    @Resource
    CustomerCouponMapper customerCouponMapper;

    @Resource
    ConsignOrderService consignOrderService;

    @Resource
    AddresseeCouponService addresseeCouponService;

    @Resource
    StaffService staffService;

    @Resource
    CustomerOpenIdService customerOpenIdService;

    @Resource
    BusinessService businessService;

    @Override
    public int addCoupon(Coupon coupon) {
        return customerCouponMapper.addCustomerCoupon(coupon);
    }

    @Override
    public List<Coupon> listByCustomerNo(String customerNo) {
        return customerCouponMapper.listByCustomerNo(customerNo);
    }

    @Override
    public int getCouponByOrder(String orderNo) {
        // 获取该订单
        Order order = consignOrderService.getConsignOrderByOrderNo(orderNo);

        // 只有非员工才可以获得券
        if (staffService.getStaffByCustomerNoAndStatus(order.getCustomerNo(), StaffStateEnum.NORMAL.getType()) == null) {
            // 发一张优惠券
            addresseeCouponService.sendAddresseeAnCoupon(order.getReceiverPhone());
        }
        return 1;
    }

    @Override
    public boolean getNewGiftBag(String customerNo) {
        CustomerOpenId customerOpenId = customerOpenIdService.getByCustomerNoAndType(customerNo, AppTypeEnum.WE_APP_MARKET);
        if(customerOpenId==null){ //新用户
            return true;
        }
        Integer haveNewGiftBag = businessService.getHaveNewGiftBagByCustomerNo(customerNo);
        if(haveNewGiftBag==null||haveNewGiftBag.equals(Integer.valueOf(0))){
            return true;
        }
        return false;
    }
}
