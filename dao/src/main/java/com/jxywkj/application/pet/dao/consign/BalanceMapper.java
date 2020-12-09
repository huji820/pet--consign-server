package com.jxywkj.application.pet.dao.consign;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @ClassName BalanceMapper
 * @Description 用户余额
 * @Author LiuXiangLin
 * @Date 2019/8/20 9:39
 * @Version 1.0
 **/
@Component
public interface BalanceMapper {
    /**
     * @Author LiuXiangLin
     * @Description 通过客户主键查询余额
     * @Date 9:41 2019/8/20
     * @Param [customerNo]
     * @return java.math.BigDecimal
     **/
    BigDecimal getByCustomerNo(@Param("customerNo") String customerNo);
}
