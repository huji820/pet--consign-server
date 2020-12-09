package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.OrderPremium;

import java.util.Map;

/**
 * @author LiuXiangLin
 * @version 1.0
 * @className OrderPremiumApiService
 * @description 移动端订单补差价接口
 * @date 2019/10/10 15:10
 **/
public interface OrderPremiumApiService {
    /**
     * @param [orderPremium]
     * @return int
     * @author LiuXiangLin
     * @description 新增一条数据
     * @date 15:11 2019/10/10
     **/
    int save(OrderPremium orderPremium);

    /**
     * <p>
     * 获取充值参数
     * </p>
     *
     * @param billNo     订单编号
     * @param customerNo 用户编号
     * @param appType    app类型
     * @param ipAddress  用户ip地址
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @author LiuXiangLin
     * @date 16:05 2020/3/4
     **/
    Map<String, String> getOrderPayParam(String billNo, String customerNo, String appType, String ipAddress) throws Exception;

    /**
     * @param [billNo]
     * @return com.jxywkj.application.pet.model.consign.OrderPremium
     * @author LiuXiangLin
     * @description 通过差价单主键查询订单
     * @date 9:08 2019/10/11
     **/
    OrderPremium getByBillNo(String billNo);

    /**
     * @param [billNo]
     * @return int
     * @author LiuXiangLin
     * @description 将差价单的状态改为已支付
     * @date 9:23 2019/10/11
     **/
    int updateOrder2Paid(String billNo);

    /**
     * 通过运输单号以及补价单类型查询补价单数量
     *
     * @param orderNo  运输单号
     * @param billType 补价单类型
     * @return int
     * @author LiuXiangLin
     * @date 15:42 2019/10/26
     **/
    int countByOrderNoAndType(String orderNo, String billType);


    /**
     * 取消补价单
     *
     * @param billNo 补价单单号
     * @return int
     * @author LiuXiangLin
     * @date 17:05 2019/10/26
     **/
    int cancelBill(String billNo);

}
