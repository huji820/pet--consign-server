package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.dao.consign.ConsignOrderRefundMapper;
import com.jxywkj.application.pet.model.consign.ConsignOrderRefund;
import com.jxywkj.application.pet.service.facade.consign.ConsignOrderRefundService;
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
 * @className ConsignOrderRefundServiceImpl
 * @date 2019/11/27 10:09
 **/
@Service
public class ConsignOrderRefundServiceImpl implements ConsignOrderRefundService {
    @Resource
    ConsignOrderRefundMapper consignOrderRefundMapper;

    @Override
    public int save(ConsignOrderRefund consignOrderRefund) {
        return consignOrderRefundMapper.save(consignOrderRefund);
    }

    @Override
    public List<ConsignOrderRefund> listByOrderNo(String orderNo) {
        return consignOrderRefundMapper.listByOrderNo(orderNo);
    }

    @Override
    public int updateOrderState(String refundOrderNo, String state) {
        return consignOrderRefundMapper.updateOrderState(refundOrderNo, state);
    }
}
