package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.OrderRemarks;

/**
 * @ClassName OrderRemarksService
 * @Description 订单备注
 * @Author LiuXiangLin
 * @Date 2019/9/23 11:17
 * @Version 1.0
 **/
public interface OrderRemarksService {
    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 添加一条数据
     * @Date 11:18 2019/9/23
     * @Param [orderRemarks ]
     **/
    int saveRemarks(OrderRemarks orderRemarks);
}
