package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.Order;
import com.jxywkj.application.pet.model.consign.OrderResource;
import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.model.consign.dto.OrderUpdateDTO;
import com.jxywkj.application.pet.model.consign.params.StaffOrderQueryDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName ConsignOrderResourceMapper
 * @Description: 资源Mapper
 * @Version 1.0
 **/
@Component
public interface ConsignOrderResourceMapper {

    /**
     * 添加资源
     * @param orderResource
     * @return
     */
    int save(@Param("orderResource")OrderResource orderResource);

    /**
     * 通过资源池外键删除资源
     * @param resourcePoolNo
     * @return
     */
    int deleteByResourcePoolNo(@Param("resourcePoolNo")String resourcePoolNo);

    /**
     * 通过资源池外键获取资源
     * @param resourcePoolNo
     * @return
     */
    List<OrderResource> listByResourcePoolNo(@Param("resourcePoolNo")String resourcePoolNo);
}
