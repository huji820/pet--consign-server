package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.params.OfflineWorkOrderDTO;

import java.util.Date;


/**
 * @ClassName GoingAbroadService
 * @Description: 出国板块Service
 * @Author zhouxiaojian
 * @Date 2020/7/23 11:01
 * @Version 1.0
 **/
public interface GoingAbroadService {

    /**
     *新增出国订单
     * @param offlineWorkOrderDTO
     * @return java.lang.String
     * @throws Exception
     * @author zhouxiaojian
     * @date 11:04 2020/07/23
     */
    String insertOrder(OfflineWorkOrderDTO offlineWorkOrderDTO)throws Exception;

}
