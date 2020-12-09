package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.OrderResource;
import com.jxywkj.application.pet.model.consign.OrderResourcePool;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName ConsignOrderResourcePoolMapper
 * @Description: 资源池Mapper
 * @Version 1.0
 **/
@Component
public interface ConsignOrderResourcePoolMapper {

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
    int deleteByOrderNoAndNode(@Param("orderNo") String orderNo,@Param("node")String node);

    /**
     * 通过订单编号和节点名称获取资源池数据
     * @param orderNo
     * @param node
     * @return
     */
    List<OrderResourcePool> listByOrderNoAndNode(@Param("orderNo") String orderNo,@Param("node")String node);

    int deleteById(@Param("id")Integer id);
}
