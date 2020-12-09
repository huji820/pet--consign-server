package com.jxywkj.application.pet.service.facade.common;

import com.jxywkj.application.pet.model.common.Customer;

/**
 * @ClassName CustomerService
 * @Description: 用户Service
 * @Author Aze
 * @Date 2019/7/13 10:53
 * @Version 1.0
 **/
public interface CustomerService {

    /**
     * <p>
     * 更新用户
     * </p>
     *
     * @param customer
     * @return void
     * @author LiuXiangLin
     * @date 15:53 2020/3/6
     **/
    void updateCustomer(Customer customer);


    /**
     * <p>
     * 新增用户
     * </p>
     *
     * @param customer 用户对象
     * @return void
     * @author LiuXiangLin
     * @date 15:53 2020/3/6
     **/
    void insetCustomer(Customer customer);

    /**
     * @return com.jxywkj.application.pet.model.common.Customer
     * @Author LiuXiangLin
     * @Description 通过电话号码查询客户对象
     * @Date 11:55 2019/7/19
     * @Param [phoneNumber]
     **/
    Customer getCustomerByPhoneNumber(String phoneNumber);


    /**
     * @return void
     * @Author LiuXiangLin
     * @Description 充值
     * @Date 16:27 2019/8/19
     * @Param [orderNo]
     **/
    void rechargeCustomerBalance(String orderNo);


    /**
     * @return com.jxywkj.application.pet.model.common.Customer
     * @Author LiuXiangLin
     * @Description 通过用户主键查询用户数据
     * @Date 18:37 2019/8/19
     * @Param [customerNo]
     **/
    Customer getCustomerByCustomerNo(String customerNo);

    /**
     * @param [phone]
     * @return int
     * @author LiuXiangLin
     * @description 通过电话号码删除数据
     * @date 14:21 2019/10/15
     **/
    int deleteByPhone(String phone);


    /**
     * <p>
     * 通过unionid查询用户
     * </p>
     *
     * @param unionId 微信unionid
     * @return com.jxywkj.application.pet.model.common.Customer
     * @author LiuXiangLin
     * @date 17:56 2020/2/29
     **/
    Customer getCustomerByUnionId(String unionId);

}
