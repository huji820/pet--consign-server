package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.common.utils.StringUtils;
import com.jxywkj.application.pet.dao.consign.ConsignOrderEvaluateMapper;
import com.jxywkj.application.pet.model.consign.ConsignOrderEvaluate;
import com.jxywkj.application.pet.service.facade.business.ConsignOrderEvaluateService;
import com.yangwang.sysframework.utils.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className ConsignOrderEvaluateServiceImpl
 * @date 2019/12/13 11:00
 **/
@Service
public class ConsignOrderEvaluateServiceImpl implements ConsignOrderEvaluateService {
    @Resource
    ConsignOrderEvaluateMapper consignOrderEvaluateMapper;

    @Override
    public int save(ConsignOrderEvaluate consignOrderEvaluate) {
        if (StringUtils.isBlank(consignOrderEvaluate.getEvaluator())
                || consignOrderEvaluate.getOrder() == null
                || StringUtils.isBlank(consignOrderEvaluate.getOrder().getOrderNo())) {
            return 0;
        }

        consignOrderEvaluate.setDateTime(DateUtil.formatFull(new Date()));

        return consignOrderEvaluateMapper.save(consignOrderEvaluate);
    }
}
