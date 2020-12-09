package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.StationWithdraw;

import java.math.BigDecimal;

/**
 * <p>
 * 站点返利
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className StationBalanceBufferService
 * @date 2019/12/6 17:27
 **/
public interface StationBalanceBufferService {
    /**
     * <p>
     * 新增一条数据
     * </p>
     *
     * @param stationWithdraw 站点提现对象
     * @return int
     * @author LiuXiangLin
     * @date 17:29 2019/12/6
     **/
    int saveWithdraw(StationWithdraw stationWithdraw);

    /**
     * <p>
     * 通过商家提现单号删除数据
     * </p>
     *
     * @param withdrawNo 提现单号
     * @return int
     * @author LiuXiangLin
     * @date 17:30 2019/12/6
     **/
    int deleteByWithdrawNo(String withdrawNo);

    /**
     * <p>
     * 站点编号
     * </p>
     *
     * @param stationNo 站点编号
     * @return java.math.BigDecimal
     * @author LiuXiangLin
     * @date 17:31 2019/12/6
     **/
    BigDecimal getTotalAmount(String stationNo);
}
