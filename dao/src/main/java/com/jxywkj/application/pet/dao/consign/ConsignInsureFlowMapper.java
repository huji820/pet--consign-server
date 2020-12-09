package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.ConsignInsureFlow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 运输投保流水
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className ConsignInsureFlowMapper
 * @date 2019/12/30 18:30
 **/
@Mapper
public interface ConsignInsureFlowMapper {
    /**
     * <p>
     * 添加一条流水
     * </p>
     *
     * @param consignInsureFlow 流水对象
     * @return int
     * @author LiuXiangLin
     * @date 18:31 2019/12/30
     **/
    int save(@Param("consignInsureFlow") ConsignInsureFlow consignInsureFlow);
}
