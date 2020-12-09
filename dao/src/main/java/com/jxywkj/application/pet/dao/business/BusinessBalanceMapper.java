package com.jxywkj.application.pet.dao.business;

import com.jxywkj.application.pet.model.business.BusinessBalance;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * @author LiuXiangLin
 * @version 1.0
 * @className BusinessBalanceMapper
 * @description 商家返利
 * @date 2019/10/12 10:43
 **/
public interface BusinessBalanceMapper {
    /**
     * @param [businessRebate]
     * @return int
     * @author LiuXiangLin
     * @description 更新商家余额
     * @date 16:30 2019/10/12
     **/
    int saveOrUpdateRebate(@Param("businessBalance") BusinessBalance businessBalance);

    /**
     * 通过商家编号查询返利
     *
     * @param businessNo 商家编号
     * @return businessRebate
     * @author LiuXiangLin
     * @date 11:27 2019/10/31
     **/
    BusinessBalance getByBusinessNo(@Param("businessNo") String businessNo);

    /**
     * <p>
     * 给站点减去指定的金额
     * </p>
     *
     * @param businessNo 站点编号
     * @param amount     减去的金额
     * @return int
     * @author LiuXiangLin
     * @date 17:25 2019/12/5
     **/
    int subtractAmount(@Param("businessNo") String businessNo, @Param("amount") BigDecimal amount);
}
