package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.StationBalance;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @ClassName StationRebateMapper
 * @Description 站点返利汇总金额
 * @Author LiuXiangLin
 * @Date 2019/8/26 13:59
 * @Version 1.0
 **/
@Component
public interface StationBalanceMapper {

    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 添加一条数据
     * @Date 14:00 2019/8/26
     * @Param [stationBalance]
     **/
    int saveOrUpdateRebate(@Param("stationBalance") StationBalance stationBalance);

    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 减去站点的指定金额
     * @Date 15:30 2019/8/26
     * @Param [stationNo, subtractAmount]
     **/
    int subtractTotalAmountByWithdraw(@Param("stationNo") String stationNo, @Param("subtractAmount") BigDecimal subtractAmount);

    /**
     * @return java.math.BigDecimal
     * @Author LiuXiangLin
     * @Description 通过站点编号获取汇总数据
     * @Date 13:58 2019/9/30
     * @Param [stationNo]
     **/
    BigDecimal getTotalByStationNo(@Param("stationNo") int stationNo);

}
