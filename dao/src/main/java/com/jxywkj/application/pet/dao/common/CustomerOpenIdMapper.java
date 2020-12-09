package com.jxywkj.application.pet.dao.common;

import com.jxywkj.application.pet.model.common.CustomerOpenId;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户openid
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className CustomerOpenIdMapper
 * @date 2020/3/4 10:04
 **/
@Mapper
public interface CustomerOpenIdMapper {
    /**
     * <p>
     * 通过customerNo和appType查询数据
     * </p>
     *
     * @param customerNo customerNo
     * @param appType    app类型
     * @return com.jxywkj.application.pet.model.common.CustomerOpenId
     * @author LiuXiangLin
     * @date 10:05 2020/3/4
     **/
    CustomerOpenId getByCustomerNoAndType(@Param("customerNo") String customerNo, @Param("appType") String appType);

    /**
     * <p>
     * 新增或者修改
     * </p>
     *
     * @param customerOpenId 用户openid
     * @return int
     * @author LiuXiangLin
     * @date 10:09 2020/3/4
     **/
    int saveOrUpdate(@Param("customerOpenId") CustomerOpenId customerOpenId);

}
