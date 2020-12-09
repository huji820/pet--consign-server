package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.dao.consign.OrderFieldMapper;
import com.jxywkj.application.pet.model.consign.OrderField;
import com.jxywkj.application.pet.service.facade.consign.OrderFieldService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName OrderFieldServiceImpl
 * @Description
 * @Author LiuXiangLin
 * @Date 2019/9/25 11:14
 * @Version 1.0
 **/
@Service
public class OrderFieldServiceImpl implements OrderFieldService {
    @Resource
    OrderFieldMapper orderFieldMapper;

    @Override
    public List<OrderField> listAll() {
        return orderFieldMapper.listAll();
    }
}
