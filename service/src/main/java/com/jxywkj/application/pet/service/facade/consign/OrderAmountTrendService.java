package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.model.consign.params.OrderAmountTrendDTO;
import com.yangwang.sysframework.utils.Pagination;


/**
 * <p>
 * 订单金额去向
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className OrderAmountTrendService
 * @date 2020/1/6 16:49
 **/
public interface OrderAmountTrendService {
    /**
     * <p>
     * 通过参数查询数据
     * </p>
     *
     * @param orderAmountTrendDTO 查询参数
     * @param staff               员工
     * @return com.yangwang.sysframework.utils.Pagination
     * @author LiuXiangLin
     * @date 16:53 2020/1/6
     **/
    Pagination listByParam(OrderAmountTrendDTO orderAmountTrendDTO, Staff staff);

}
