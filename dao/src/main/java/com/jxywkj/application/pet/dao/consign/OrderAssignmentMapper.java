package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.OrderAssignment;
import com.jxywkj.application.pet.model.consign.vo.OrderAssignmentVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName OrderAssignmentMapper
 * @Description 订单分配
 * @Author LiuXiangLin
 * @Date 2019/8/24 14:20
 * @Version 1.0
 **/
@Component
public interface OrderAssignmentMapper {
    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 添加一条数据
     * @Date 14:21 2019/8/24
     * @Param [orderAssignment]
     **/
    int saveOrderAssignment(@Param("orderAssignment") OrderAssignment orderAssignment);


    /**
     * @Author LiuXiangLin
     * @Description 通过城市查询数据
     * @Date 15:17 2019/8/24
     * @Param [cityName]
     * @return java.util.List<com.jxywkj.application.pet.model.consign.vo.OrderAssignmentVO>
     **/
    List<OrderAssignmentVO> listByCityName(@Param("cityName") String cityName);

    /**
     * @Author LiuXiangLin
     * @Description 通过订单号查询数据
     * @Date 17:45 2019/9/23
     * @Param [orderNo]
     * @return java.util.List<com.jxywkj.application.pet.model.consign.OrderAssignment>
     **/
    List<OrderAssignment> listByOrderNo(@Param("orderNo") String orderNo );
    
    /**
     * @author LiuXiangLin
     * @description 通过订单编号查询所有已分配人员的电话号码·
     * @date 16:20 2019/10/14
     * @param [orderNo]
     * @return java.util.List<java.lang.String>
     **/
    List<String> listPhoneByOrderNo(@Param("orderNo") String orderNo);
    
    /**
     * @author LiuXiangLin
     * @description 通过订单编号查询分配数量
     * @date 16:41 2019/10/14
     * @param [orderNo]
     * @return int
     **/
    int countByOrderNo(String orderNo);
}
