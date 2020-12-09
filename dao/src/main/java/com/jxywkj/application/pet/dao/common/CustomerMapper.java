package com.jxywkj.application.pet.dao.common;

import com.jxywkj.application.pet.model.common.Customer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @ClassName CustomerMapper
 * @Description: 用户Mapper
 * @Author Aze
 * @Date 2019/7/13 11:09
 * @Version 1.0
 **/
@Component
public interface CustomerMapper {
    void insetCustomer(Customer customer);

    void updateCustomer(Customer customer);

    /**
     * @return com.jxywkj.application.pet.model.common.Customer
     * @Author LiuXiangLin
     * @Description 通过电话号码查询用户信息
     * @Date 11:55 2019/7/19
     * @Param [phoneNumber]
     **/
    Customer getCustomerByPhoneNumber(String phoneNumber);

    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 添加用于余额
     * @Date 16:17 2019/8/19
     * @Param [amount]
     **/
    int updateCustomerAddBalance(@Param("amount") BigDecimal amount, @Param("customerNo") String customerNo);

    /**
     * @return com.jxywkj.application.pet.model.common.Customer
     * @Author LiuXiangLin
     * @Description 通过客户主键查新数据
     * @Date 18:36 2019/8/19
     * @Param [customerNo]
     **/
    Customer getCustomerByCustomerNo(@Param("customerNo") String customerNo);


    /**
     * @return int
     * @Param [phone]
     * @Author LiuXiangLin
     * @Description 通过电话号码删除数据
     * @Date 14:33 2019/10/15
     **/
    int deleteByPhone(@Param("phone") String phone);

    /**
     * <p>
     * 通过unionid查询用户
     * </p>
     *
     * @param unionId 微信unionid
     * @return com.jxywkj.application.pet.model.common.Customer
     * @author LiuXiangLin
     * @date 17:59 2020/2/29
     **/
    Customer getByUnionId(@Param("unionId") String unionId);


}
