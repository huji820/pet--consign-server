package com.jxywkj.application.pet.service.facade.business;

import com.jxywkj.application.pet.model.business.Business;
import com.jxywkj.application.pet.model.business.BusinessBalance;
import com.jxywkj.application.pet.model.consign.Order;

import java.math.BigDecimal;

/**
 * @author LiuXiangLin
 * @version 1.0
 * @className BusinessBalanceService
 * @description 商家返现
 * @date 2019/10/12 16:37
 **/
public interface BusinessBalanceService {
    /**
     * @param [businessRebate]
     * @return int
     * @author LiuXiangLin
     * @description 更新商家返现金额
     * @date 16:38 2019/10/12
     **/
    int saveOrUpdate(BusinessBalance businessBalance);


    /**
     * 添加一条返现金额
     *
     * @param order    订单
     * @param business 商家
     * @param amount   返利金额
     * @return int
     * @author LiuXiangLin
     * @date 17:38 2019/10/25
     **/
    int saveRebateAmount(Order order, Business business, BigDecimal amount);

    /**
     * 通过商家编号查询返利
     *
     * @param businessNo 编号
     * @return com.jxywkj.application.pet.model.business.BusinessBalance
     * @author LiuXiangLin
     * @date 11:26 2019/10/31
     **/
    BusinessBalance getByBusinessNo(String businessNo);

    /**
     * <p>
     * 给站点减去指定的金额
     * </p>
     *
     * @param businessNo 商家编号
     * @param amount     减去的金额
     * @return int
     * @author LiuXiangLin
     * @date 17:24 2019/12/5
     **/
    int subtract(String businessNo, BigDecimal amount);
}
