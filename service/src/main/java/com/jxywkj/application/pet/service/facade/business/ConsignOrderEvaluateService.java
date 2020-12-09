package com.jxywkj.application.pet.service.facade.business;

import com.jxywkj.application.pet.model.consign.ConsignOrderEvaluate;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className ConsignOrderEvaluateService
 * @date 2019/12/13 10:52
 **/
public interface ConsignOrderEvaluateService {
    /**
     * <p>
     * 新增一条数据
     * </p>
     *
     * @param consignOrderEvaluate 评分对象
     * @return int
     * @author LiuXiangLin
     * @date 10:57 2019/12/13
     **/
    int save(ConsignOrderEvaluate consignOrderEvaluate);
}
