package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.OrderField;

import java.util.List;

/**
 * @ClassName OrderFieldService
 * @Description 订单字段
 * @Author LiuXiangLin
 * @Date 2019/9/25 11:13
 * @Version 1.0
 **/
public interface OrderFieldService {
    /**
     * @Author LiuXiangLin
     * @Description 查询所有的数据
     * @Date 11:14 2019/9/25
     * @Param []
     * @return java.util.List<com.jxywkj.application.pet.model.consign.OrderField>
     **/
    List<OrderField> listAll();
}
