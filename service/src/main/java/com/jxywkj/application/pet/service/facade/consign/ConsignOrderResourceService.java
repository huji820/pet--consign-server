package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.OrderResource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName ConsignOrderResourceMapper
 * @Description: 资源Service
 * @Version 1.0
 **/
public interface ConsignOrderResourceService {

    /**
     * 添加资源
     * @param orderResource
     * @return
     */
    int save(OrderResource orderResource);

    /**
     * 通过资源池外键删除资源
     * @param resourcePoolNo
     * @return
     */
    int deleteByResourcePoolNo(String resourcePoolNo);

    /**
     * 通过资源池外键获取资源
     * @param resourcePoolNo
     * @return
     */
    List<OrderResource> listByResourcePoolNo(String resourcePoolNo);
}
