package com.jxywkj.application.pet.service.facade.business;

import com.jxywkj.application.pet.model.business.BusinessWithdraw;

import java.math.BigDecimal;

/**
 * <p>
 * 返利临时减扣
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className BusinessBalanceBufferService
 * @date 2019/12/6 16:49
 **/
public interface BusinessBalanceBufferService {
    /**
     * <p>
     * 添加一条数据
     * </p>
     *
     * @param businessWithdraw 提现对象
     * @return int
     * @author LiuXiangLin
     * @date 16:50 2019/12/6
     **/
    int saveWithdraw(BusinessWithdraw businessWithdraw);

    /**
     * <p>
     * 通过商家编号查询所有的金额总和
     * </p>
     *
     * @param businessNo 商家编号
     * @return int
     * @author LiuXiangLin
     * @date 16:51 2019/12/6
     **/
    BigDecimal getTotalAmount(String businessNo);

    /**
     * <p>
     * 通过提现主键删除
     * </p>
     *
     * @param withdrawNo
     * @return int
     * @author LiuXiangLin
     * @date 17:11 2019/12/6
     **/
    int deleteByWithdrawNo(String withdrawNo);
}
