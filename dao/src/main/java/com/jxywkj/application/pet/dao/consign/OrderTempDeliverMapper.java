package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.OrderTempDeliver;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName OrderTempDeliverMapper
 * @Description 订单临时配送
 * @Author LiuXiangLin
 * @Date 2019/9/25 9:55
 * @Version 1.0
 **/
@Mapper
public interface OrderTempDeliverMapper {

    /**
     * @Author LiuXiangLin
     * @Description 添加一条数据
     * @Date 9:55 2019/9/25
     * @Param [orderTempDeliver]
     * @return int
     **/
    int save(@Param("orderTempDeliver") OrderTempDeliver orderTempDeliver);
}
