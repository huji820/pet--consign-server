package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.OrderAssignment;
import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.model.consign.vo.OrderAssignmentVO;

import java.util.List;

/**
 * @ClassName OrderAssignmentService
 * @Description 订单分配
 * @Author LiuXiangLin
 * @Date 2019/8/24 14:25
 * @Version 1.0
 **/
public interface OrderAssignmentService {
    /**
     * @Author LiuXiangLin
     * @Description 添加一条数据
     * @Date 14:26 2019/8/24
     * @Param [orderNo, staffList, staffNoArray]
     * @return int
     **/
    int saveOrderAssignment(String orderNo, Staff staff, List<Integer> staffNoArray);

    /**
     * @Author LiuXiangLin
     * @Description 通过城市名称查询订单
     * @Date 15:51 2019/8/24
     * @Param [cityName]
     * @return java.util.List<com.jxywkj.application.pet.model.consign.vo.OrderAssignmentVO>
     **/
    List<OrderAssignmentVO> listByCityName(String cityName);
    
    /**
     * @Author LiuXiangLin
     * @Description 通过订单号查询数据
     * @Date 17:55 2019/9/23
     * @Param [orderNo]
     * @return java.util.List<com.jxywkj.application.pet.model.consign.OrderAssignment>
     **/
    List<OrderAssignment> listByOrderNo(String orderNo);
    
    /**
     * @author LiuXiangLin
     * @description 通过订单号查询所有已经分配人员的电话
     * @date 16:20 2019/10/14
     * @param [orderNo]
     * @return java.util.List<java.lang.String>
     **/
    List<String> listPhoneByOrderNo(String orderNo);

    /**
     * @author LiuXiangLin
     * @description 通过订单编号查询分配的人数
     * @date 16:32 2019/10/14
     * @param [orderNo]
     * @return int
     **/
    int countByOrderNo(String orderNo);

}
