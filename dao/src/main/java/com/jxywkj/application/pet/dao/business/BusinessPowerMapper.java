package com.jxywkj.application.pet.dao.business;

import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商家权重
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className BusinessPowerMapper
 * @date 2020/3/17 15:18
 **/
public interface BusinessPowerMapper {
    /**
     * <p>
     * 更新商家权权重
     * </p>
     *
     * @param businessNo 商家编号
     * @param power      权重
     * @return int
     * @author LiuXiangLin
     * @date 15:19 2020/3/17
     **/
    int update(@Param("businessNo") String businessNo, @Param("power") int power);
}
