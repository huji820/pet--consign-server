package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.ConsignOrderEvaluate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 订单评分
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className ConsignOrderEvaluateMapper
 * @date 2019/12/13 10:25
 **/
@Mapper
public interface ConsignOrderEvaluateMapper {
    /**
     * <p>
     * 添加一条评分
     * </p>
     *
     * @param consignOrderEvaluate 评分对象
     * @return int
     * @author LiuXiangLin
     * @date 10:31 2019/12/13
     **/
    int save(@Param("consignOrderEvaluate") ConsignOrderEvaluate consignOrderEvaluate);
}
