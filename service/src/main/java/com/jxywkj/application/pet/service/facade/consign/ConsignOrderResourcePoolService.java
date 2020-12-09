package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.OrderResourcePool;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName ConsignOrderResourcePoolMapper
 * @Description: 资源池Service
 * @Version 1.0
 **/
public interface ConsignOrderResourcePoolService {

    /**
     * 添加资源池数据
     * @param orderResourcePool
     * @return
     */
    int save(OrderResourcePool orderResourcePool);

    /**
     * 通过订单编号和节点名称删除资源池数据
     * @param orderNo
     * @param node
     * @return
     */
    int deleteByOrderNoAndNode(String orderNo, String node);

    /**
     * 通过订单编号和节点名称获取资源池数据
     * @param orderNo
     * @param node
     * @return
     */
    List<OrderResourcePool> listByOrderNoAndNode(String orderNo, String node);

    /**
     * @Description: 通过id删除资源池数据
     * @Author: zxj
     * @Date: 2020/10/29 13:45
     * @param id:
     * @return: int
     **/
    int deleteById(Integer id);
}
