package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.dao.consign.OrderMediaMapper;
import com.jxywkj.application.pet.model.consign.OrderMedia;
import com.jxywkj.application.pet.service.facade.consign.ConsignOrderService;
import com.jxywkj.application.pet.service.facade.consign.OrderMediaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName OrderMediaServiceImpl
 * @Description
 * @Author LiuXiangLin
 * @Date 2019/7/22 17:18
 * @Version 1.0
 **/
@Service
public class OrderMediaServiceImpl implements OrderMediaService {

    @Resource
    OrderMediaMapper orderMediaMapper;

    @Resource
    ConsignOrderService consignOrderService;

    @Override
    public int addAMedias(OrderMedia orderMedia) {
        return orderMediaMapper.addAMedia(orderMedia);
    }

    @Override
    public int addAMediaList(List<OrderMedia> orderMediaList) {
        return orderMediaMapper.addMediaList(orderMediaList);
    }

    @Override
    public OrderMedia getByAddress(String address) {
        return orderMediaMapper.getByAddress(address);
    }

    @Override
    public int updateOrderMediaSn(OrderMedia orderMedia) {
        return orderMediaMapper.updateOrderMediaSn(orderMedia);
    }
}
