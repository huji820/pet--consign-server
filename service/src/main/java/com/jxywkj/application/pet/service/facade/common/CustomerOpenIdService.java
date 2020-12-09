package com.jxywkj.application.pet.service.facade.common;

import com.jxywkj.application.pet.common.enums.AppTypeEnum;
import com.jxywkj.application.pet.model.common.CustomerOpenId;

/**
 * <p>
 * 用户openid
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className CustomerOpenIdService
 * @date 2020/3/4 10:28
 **/
public interface CustomerOpenIdService {
    /**
     * <p>
     * 通过customerNo和app类型获取数据
     * </p>
     *
     * @param customerNo  用户编号
     * @param appTypeEnum app类型
     * @return com.jxywkj.application.pet.model.common.CustomerOpenId
     * @author LiuXiangLin
     * @date 10:29 2020/3/4
     **/
    CustomerOpenId getByCustomerNoAndType(String customerNo, AppTypeEnum appTypeEnum);

    /**
     * <p>
     * 保存或者更新数据
     * </p>
     *
     * @param customerOpenId 用户openid
     * @return int
     * @author LiuXiangLin
     * @date 10:30 2020/3/4
     **/
    int saveOrUpdate(CustomerOpenId customerOpenId);
}
