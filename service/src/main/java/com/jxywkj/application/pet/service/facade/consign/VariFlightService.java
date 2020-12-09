package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.Order;
import com.yangwang.sysframework.veriflight.dto.PushFlightResponseData;

import java.util.List;

/**
 * <p>
 * 飞常准
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className VariFlightService
 * @date 2019/12/7 17:41
 **/
public interface VariFlightService {
    /**
     * <p>
     * 订单订阅飞常准
     * </p>
     *
     * @param order 订单对象
     * @return int
     * @author LiuXiangLin
     * @date 17:42 2019/12/7
     **/
    int orderSubscribe(Order order);

    /**
     * <p>
     * 订单推送
     * </p>
     *
     * @param responseData 推送内容
     * @return int
     * @author LiuXiangLin
     * @date 17:43 2019/12/7
     **/
    int orderPush(PushFlightResponseData responseData);
}
