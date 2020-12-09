package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.Order;
import com.jxywkj.application.pet.model.consign.params.StaffOrderQueryDTO;

import java.util.List;

/**
 * @ClassName PortService
 * @Description 出港入港
 * @Author LiuXiangLin
 * @Date 2019/8/9 14:07
 * @Version 1.0
 **/
public interface PortService {

    /**
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Order>
     * @Author LiuXiangLin
     * @Description 通过类型获取订单
     * @Date 14:10 2019/8/9
     * @Param [staffOrderQueryDTO]
     **/
    List<Order> listPortOrderByType(StaffOrderQueryDTO staffOrderQueryDTO);

    /**
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Order>
     * @Author LiuXiangLin
     * @Description 通过类型获取管理员订单
     * @Date 16:40 2019/9/17
     * @Param [StaffOrderQueryDTO staffOrderQueryDTO]
     **/
    List<Order> listAdminOrderByType(StaffOrderQueryDTO staffOrderQueryDTO);

    /**
     * @author LiuXiangLin
     * @description 通过多个参数查询工单列表
     * @date 15:31 2019/10/11
     * @param [queryParamStr]
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Order>
     **/
    List<Order> listStaffOrderListByParam(String queryParamStr) throws Exception;
}
