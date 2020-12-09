package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.ConsignOrderRefund;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 运输退款
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className ConsignOrderRefundMapper
 * @date 2019/11/27 9:29
 **/
@Mapper
public interface ConsignOrderRefundMapper {
    /**
     * <p>
     * 新增一条数据
     * </p>
     *
     * @param consignOrderRefund 退款单据对象
     * @return int
     * @author LiuXiangLin
     * @date 9:31 2019/11/27
     **/
    int save(@Param("consignOrderRefund") ConsignOrderRefund consignOrderRefund);

    /**
     * <p>
     * 通过原单号查询所有的退款单据
     * </p>
     *
     * @param orderNo 订单编号
     * @return java.util.List<com.jxywkj.application.pet.model.consign.ConsignOrderRefund>
     * @author LiuXiangLin
     * @date 9:31 2019/11/27
     **/
    List<ConsignOrderRefund> listByOrderNo(@Param("orderNo") String orderNo);

    /**
     * <p>
     * 更新订单状态
     * </p>
     *
     * @param refundOrderNo 退款单号
     * @param state         退款单状态
     * @return int
     * @author LiuXiangLin
     * @date 9:32 2019/11/27
     **/
    int updateOrderState(@Param("refundOrderNo") String refundOrderNo, @Param("state") String state);

}
