package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.OrderTempDeliver;

import java.io.IOException;

/**
 * @ClassName OrderTempDeliverService
 * @Description 订单临时配送
 * @Author LiuXiangLin
 * @Date 2019/9/25 10:02
 * @Version 1.0
 **/
public interface OrderTempDeliverService {
    /**
     * @Author LiuXiangLin
     * @Description 新增一条数据
     * @Date 10:03 2019/9/25
     * @Param [orderTempDeliver]
     * @return int
     **/
    int save(OrderTempDeliver orderTempDeliver) throws Exception;
}
