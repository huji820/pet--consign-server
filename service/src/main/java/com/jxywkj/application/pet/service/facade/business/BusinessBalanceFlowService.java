package com.jxywkj.application.pet.service.facade.business;

import com.jxywkj.application.pet.common.enums.BalanceFlowTypeEnum;
import com.jxywkj.application.pet.model.business.Business;
import com.jxywkj.application.pet.model.business.BusinessBalanceFlow;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 站点返利
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className BusinessBalanceFlowService
 * @date 2019/12/30 11:06
 **/
public interface BusinessBalanceFlowService {
    /**
     * <p>
     * 添加一条流水（修改余额之后调用）
     * </p>
     *
     * @param business            站点
     * @param amount              流水金额
     * @param billNo              单号
     * @param linkNo              相关单号
     * @param balanceFlowTypeEnum 流水类型
     * @return int
     * @author LiuXiangLin
     * @date 11:08 2019/12/30
     **/
    int save(Business business, BigDecimal amount, String billNo, String linkNo, BalanceFlowTypeEnum balanceFlowTypeEnum);

    /**
     * <p>
     * 通过订单类型查询数据
     * </p>
     *
     * @param businessNo 站点编号
     * @param offset     排除条数
     * @param limit      显示条数
     * @return java.util.List<com.jxywkj.application.pet.model.business.BusinessBalanceFlow>
     * @author LiuXiangLin
     * @date 11:10 2019/12/30
     **/
    List<BusinessBalanceFlow> listByBusiness(String businessNo, int offset, int limit);
}
