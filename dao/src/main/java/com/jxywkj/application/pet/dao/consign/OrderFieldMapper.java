package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.OrderField;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName OrderFieldMapper
 * @Description 订单列
 * @Author LiuXiangLin
 * @Date 2019/9/25 9:40
 * @Version 1.0
 **/
@Mapper
public interface OrderFieldMapper {

    /**
     * @Author LiuXiangLin
     * @Description 查询所有数据
     * @Date 9:41 2019/9/25
     * @Param []
     * @return java.util.List<com.jxywkj.application.pet.model.consign.OrderField>
     **/
    List<OrderField> listAll();
}
