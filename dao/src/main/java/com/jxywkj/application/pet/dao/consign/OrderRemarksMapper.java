package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.OrderRemarks;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName OrderRemarksMapper
 * @Description 订单备注
 * @Author LiuXiangLin
 * @Date 2019/9/23 11:10
 * @Version 1.0
 **/
@Mapper
public interface OrderRemarksMapper {
    /**
     * @Author LiuXiangLin
     * @Description 添加一条备注
     * @Date 11:11 2019/9/23
     * @Param [orderRemarks]
     * @return int
     **/
    int saveRemarks(@Param("orderRemarks") OrderRemarks orderRemarks);
}
