package com.jxywkj.application.pet.service.facade.business;

import com.jxywkj.application.pet.model.business.BusinessWithdraw;
import com.jxywkj.application.pet.model.consign.StationWithdraw;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 商家提现
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className BusinessWithdrawService
 * @date 2019/12/5 14:56
 **/
public interface BusinessWithdrawService {
    /**
     * <p>
     * 添加提现
     * </p>
     *
     * @param businessNo 商家编号
     * @param amount     提现金额
     * @return int
     * @author LiuXiangLin
     * @date 14:57 2019/12/5
     **/
    int saveBusinessWithdraw(String businessNo, BigDecimal amount);

    /**
     * <p>
     * 确认提现
     * </p>
     *
     * @param withdraws 提现单号
     * @return int
     * @author LiuXiangLin
     * @date 17:16 2019/12/5
     **/
    int configWithdraw(List<BusinessWithdraw> withdraws);

    /**
     * <p>
     * 通过站点编号查询提现流水
     * </p>
     *
     * @param businessNo 商家流水
     * @param offset     排除条数
     * @param limit      显示条数
     * @return java.util.List<com.jxywkj.application.pet.model.business.BusinessWithdraw>
     * @author LiuXiangLin
     * @date 10:09 2019/12/6
     **/
    List<BusinessWithdraw> listByStationNo(String businessNo, int offset, int limit);

    /**
     * <p>
     * 驳回商家提现申请
     * </p>
     *
     * @param withdraws 提现单号
     * @return int
     * @author LiuXiangLin
     * @date 18:03 2019/12/6
     **/
    int rejectWithdraw(List<BusinessWithdraw> withdraws);

    /**
     * 查询商家提现列表
     * @param withdrawNo
     * @param businessNo
     * @param startDate
     * @param endDate
     * @param start
     * @param end
     * @return
     */
    List<BusinessWithdraw> listBusinessWithdraw(String withdrawNo, String businessNo, String startDate, String endDate, boolean active, int start, int end);


    /**
     * 查询商家提现列表
     * @param withdrawNo
     * @param businessNo
     * @param startDate
     * @param endDate
     * @return
     */
    int countBusinessWithdraw(String withdrawNo, String businessNo, String startDate, String endDate, boolean active);

}
