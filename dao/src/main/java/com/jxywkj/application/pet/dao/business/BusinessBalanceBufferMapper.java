package com.jxywkj.application.pet.dao.business;

import com.jxywkj.application.pet.model.business.BusinessBalanceBuffer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * <p>
 * 站点临时减扣金额
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className BusinessBalanceBufferMapper
 * @date 2019/12/6 16:39
 **/
@Mapper
public interface BusinessBalanceBufferMapper {
    /**
     * <p>
     * 新增一条数据
     * </p>
     *
     * @param businessBalanceBuffer 对象
     * @return int
     * @author LiuXiangLin
     * @date 16:40 2019/12/6
     **/
    int save(@Param("businessBalanceBuffer") BusinessBalanceBuffer businessBalanceBuffer);

    /**
     * <p>
     * 通过单号删除数据
     * </p>
     *
     * @param billNo 订单编号
     * @return int
     * @author LiuXiangLin
     * @date 16:41 2019/12/6
     **/
    int delete(@Param("billNo") String billNo);

    /**
     * <p>
     * 通过商家编号查询临时减扣总额
     * </p>
     *
     * @param businessNo 商家编号
     * @return java.math.BigDecimal
     * @author LiuXiangLin
     * @date 16:42 2019/12/6
     **/
    BigDecimal getTotalByBusinessNo(String businessNo);
}
