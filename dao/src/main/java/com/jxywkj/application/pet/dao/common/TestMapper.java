package com.jxywkj.application.pet.dao.common;

import com.jxywkj.application.pet.model.consign.OrderPremium;

import java.util.List;

/**
 * <p>
 * 测试使用
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className TestMapper
 * @date 2020/5/8 9:07
 **/
public interface TestMapper {
    /**
     * <p>
     * 查询已经支付但是没有将金额返给站点的补价单
     * </p>
     *
      * @param
     * @return java.util.List<com.jxywkj.application.pet.model.consign.OrderPremium>
     * @author LiuXiangLin
     * @date \ 2020/5/8
     **/
    List<OrderPremium> listUnpaid();
}
