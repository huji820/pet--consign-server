package com.jxywkj.application.pet.service.spring.common;

import com.jxywkj.application.pet.dao.common.CustomerMapper;
import com.jxywkj.application.pet.model.common.Customer;
import com.jxywkj.application.pet.model.consign.RechargeOrder;
import com.jxywkj.application.pet.service.facade.common.CustomerService;
import com.jxywkj.application.pet.service.facade.consign.RechargeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description
 * @Author Administrator
 * @Date 2019-07-13 0:00
 * @Version 1.0
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerMapper customerMapper;

    @Resource
    RechargeOrderService rechargeOrderService;

    @Override
    public void updateCustomer(Customer customer) {
        customerMapper.updateCustomer(customer);
    }

    @Override
    public void insetCustomer(Customer customer) {
        customerMapper.insetCustomer(customer);
    }

    @Override
    public Customer getCustomerByPhoneNumber(String phoneNumber) {
        return customerMapper.getCustomerByPhoneNumber(phoneNumber);
    }

    @Override
    public void rechargeCustomerBalance(String orderNo) {
        // 查询订单
        RechargeOrder rechargeOrder = rechargeOrderService.getByOrderNo(orderNo);
        if (rechargeOrder != null) {
            // 查询客户信息
            Customer customer = customerMapper.getCustomerByCustomerNo(rechargeOrder.getCustomerNo());
            if (customer != null) {
                customerMapper.updateCustomerAddBalance(rechargeOrder.getRechargeAmount(), rechargeOrder.getCustomerNo());
            }
        }

    }

    @Override
    public Customer getCustomerByCustomerNo(String customerNo) {
        return customerMapper.getCustomerByCustomerNo(customerNo);
    }

    @Override
    public int deleteByPhone(String phone) {
        return customerMapper.deleteByPhone(phone);
    }

    @Override
    public Customer getCustomerByUnionId(String unionId) {
        return customerMapper.getByUnionId(unionId);
    }

}
