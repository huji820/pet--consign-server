package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.StationBalanceBuffer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className StationBalanceBufferMapper
 * @date 2019/12/6 15:51
 **/
@Mapper
public interface StationBalanceBufferMapper {
    /**
     * <p>
     * 新增一条数据
     * </p>
     *
     * @param stationBalanceBuffer 对象
     * @return int
     * @author LiuXiangLin
     * @date 17:15 2019/12/6
     **/
    int save(@Param("stationBalanceBuffer") StationBalanceBuffer stationBalanceBuffer);

    /**
     * <p>
     * 通过单号删除数据
     * </p>
     *
     * @param billNo 单号
     * @return int
     * @author LiuXiangLin
     * @date 17:16 2019/12/6
     **/
    int delete(String billNo);

    /**
     * <p>
     * 通过站点编号查询所有的汇总金额
     * </p>
     *
     * @param stationNo 站点编号
     * @return java.math.BigDecimal
     * @author LiuXiangLin
     * @date 17:16 2019/12/6
     **/
    BigDecimal getTotalAmount(@Param("stationNo") String stationNo);

}
