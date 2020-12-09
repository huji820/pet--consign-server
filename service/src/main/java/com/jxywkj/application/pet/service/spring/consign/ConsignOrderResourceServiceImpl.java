package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.dao.consign.ConsignOrderResourceMapper;
import com.jxywkj.application.pet.model.consign.OrderResource;
import com.jxywkj.application.pet.service.facade.consign.ConsignOrderResourceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName ConsignOrderResourceMapper
 * @Description: 资源Service实现类
 * @Version 1.0
 **/
@Service
public class ConsignOrderResourceServiceImpl implements ConsignOrderResourceService {

    @Resource
    ConsignOrderResourceMapper consignOrderResourceMapper;

    @Override
    public int save(OrderResource orderResource) {
        return consignOrderResourceMapper.save(orderResource);
    }

    @Override
    public int deleteByResourcePoolNo(String resourcePoolNo) {
        return consignOrderResourceMapper.deleteByResourcePoolNo(resourcePoolNo);
    }

    @Override
    public List<OrderResource> listByResourcePoolNo(String resourcePoolNo) {
        return consignOrderResourceMapper.listByResourcePoolNo(resourcePoolNo);
    }
}
