package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.dao.consign.ConsignOrderResourcePoolMapper;
import com.jxywkj.application.pet.model.consign.OrderResourcePool;
import com.jxywkj.application.pet.service.facade.consign.ConsignOrderResourcePoolService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName ConsignOrderResourcePoolMapper
 * @Description: 资源池Service实现类
 * @Version 1.0
 **/
@Service
public class ConsignOrderResourcePoolServiceImpl implements ConsignOrderResourcePoolService {

    @Resource
    ConsignOrderResourcePoolMapper consignOrderResourcePoolMapper;

    @Override
    public int save(OrderResourcePool orderResourcePool) {
        return consignOrderResourcePoolMapper.save(orderResourcePool);
    }

    @Override
    public int deleteByOrderNoAndNode(String orderNo, String node) {
        return consignOrderResourcePoolMapper.deleteByOrderNoAndNode(orderNo, node);
    }

    @Override
    public List<OrderResourcePool> listByOrderNoAndNode(String orderNo, String node) {
        return consignOrderResourcePoolMapper.listByOrderNoAndNode(orderNo, node);
    }

    @Override
    public int deleteById(Integer id) {
        return consignOrderResourcePoolMapper.deleteById(id);
    }

}
