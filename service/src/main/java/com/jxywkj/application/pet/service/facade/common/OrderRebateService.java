package com.jxywkj.application.pet.service.facade.common;

/**
 * @author LiuXiangLin
 * @version 1.0
 * @className OrderRebateService
 * @description 订单补差价
 * @date 2019/10/12 17:25
 **/
public interface OrderRebateService {
    /**
     * @author LiuXiangLin
     * @description 添加一个订单的差价
     * @date 17:26 2019/10/12
     * @param orderNo
     * @return int
     **/
    int saveRebate(String orderNo);
}
