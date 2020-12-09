package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.Order;

public interface InsureService {

    /**
     * 添加订单保险(平安保险)
     *
     * @param order
     * @return
     */
    int addOrderInsure(Order order) throws Exception;

    /**
     * 添加订单保险(大地保险)
     *
     * @param order
     * @return
     */
    int addDaDiOrderInsure(Order order) throws Exception;

    /**
     * 修改保险投保
     *
     * @param orderNo
     * @param insureCode
     * @param status
     * @return
     */
    int updateInsureCode(String orderNo, String insureCode, short status);

    /**
     * <p>
     * 保险金额
     * </p>
     *
     * @param order 订单对象
     * @return int
     * @author LiuXiangLin
     * @date 16:56 2019/12/25
     **/
    int insureRebate(Order order);
}
