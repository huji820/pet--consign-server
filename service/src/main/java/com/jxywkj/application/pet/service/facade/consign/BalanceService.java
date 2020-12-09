package com.jxywkj.application.pet.service.facade.consign;

import java.math.BigDecimal;

/**
 * @ClassName BalanceService
 * @Description 用户余额
 * @Author LiuXiangLin
 * @Date 2019/8/20 9:44
 * @Version 1.0
 **/
public interface BalanceService {
    /**
     * <p>
     * 通过customerNo获取用户余额
     * </p>
     *
     * @param customerNo 用户编号
     * @return java.math.BigDecimal
     * @author LiuXiangLin
     * @date 16:11 2020/3/4
     **/
    BigDecimal getByCustomerNo(String customerNo);
}
