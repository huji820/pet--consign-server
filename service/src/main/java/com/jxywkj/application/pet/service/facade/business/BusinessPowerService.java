package com.jxywkj.application.pet.service.facade.business;

import com.jxywkj.application.pet.model.business.Business2;

/**
 * <p>
 * 商家权重
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className BusinessPowerService
 * @date 2020/3/17 15:12
 **/
public interface BusinessPowerService {
    /**
     * <p>
     * 获取权重值
     * </p>
     *
     * @param business2 商家对象
     * @return int
     * @author LiuXiangLin
     * @date 15:12 2020/3/17
     **/
    int get(Business2 business2);

    /**
     * <p>
     * 更新商家权重
     * </p>
     *
     * @param businessNo 商家编号
     * @param power      权重
     * @return int
     * @author LiuXiangLin
     * @date 15:13 2020/3/17
     **/
    int update(String businessNo, int power);
}
