package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.OrderTakeDetail;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 提货详情
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className OrderTakeDetailMapper
 * @date 2020/4/8 9:31
 **/
public interface OrderTakeDetailMapper {
    /**
     * <p>
     * 新增一条数据
     * </p>
     *
     * @param orderTakeDetail 提货详情对象
     * @return int
     * @author LiuXiangLin
     * @date 9:32 2020/4/8
     **/
    int saveOrUpdate(@Param("orderTakeDetail") OrderTakeDetail orderTakeDetail);

    /**
     * <p>
     * 通过订单编号查询
     * </p>
     *
     * @param orderNo 订单编号
     * @return com.jxywkj.application.pet.model.consign.OrderTakeDetail
     * @author LiuXiangLin
     * @date 9:33 2020/4/8
     **/
    OrderTakeDetail getByOrderNo(@Param("orderNo") String orderNo);
}
