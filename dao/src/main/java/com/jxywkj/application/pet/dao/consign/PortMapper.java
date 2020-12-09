package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.Order;
import com.jxywkj.application.pet.model.consign.params.StaffOrderQueryDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName PortMapper
 * @Description 出港入港
 * @Author LiuXiangLin
 * @Date 2019/8/9 14:15
 * @Version 1.0
 **/
public interface PortMapper {

    /**
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Order>
     * @Author LiuXiangLin
     * @Description 通过站点和运输状态查询数据
     * @Date 15:54 2019/8/9
     * @Param [stationNo, type,staffN]
     **/
    List<Order> listByOrderTypeAndStation(@Param("staffOrderQueryDTO") StaffOrderQueryDTO staffOrderQueryDTO);

    /**
     * @Author LiuXiangLin
     * @Description 通过站点和运输状态查询管理员数据
     * @Date 16:41 2019/9/17
     * @Param [staffOrderQueryDTO]
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Order>
     **/
    List<Order> listAdminByOrderTypeAndStation(@Param("staffOrderQueryDTO") StaffOrderQueryDTO staffOrderQueryDTO);
}
