package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.dao.consign.OrderFlowTrendMapper;
import com.jxywkj.application.pet.model.consign.OrderFlowTrend;
import com.jxywkj.application.pet.service.facade.consign.OrderFlowTrendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className OrderFlowTrendServiceImpl
 * @date 2020/1/7 16:34
 **/
@Service
public class OrderFlowTrendServiceImpl implements OrderFlowTrendService {
    @Resource
    OrderFlowTrendMapper orderFlowTrendMapper;

    @Override
    public List<OrderFlowTrend> listByOrderNo(String orderNo) {
        return orderFlowTrendMapper.listByOrderNo(orderNo);
    }
}
