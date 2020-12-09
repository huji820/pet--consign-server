package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.common.utils.CollectionUtils;
import com.jxywkj.application.pet.common.utils.StringUtils;
import com.jxywkj.application.pet.dao.consign.StationAppraiseMapper;
import com.jxywkj.application.pet.model.business.Business;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.model.consign.StationAppraise;
import com.jxywkj.application.pet.service.facade.consign.StationAppraiseService;
import com.yangwang.sysframework.utils.DateUtil;
import com.yangwang.sysframework.utils.TypeConvertUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  站点服务评价
 * </p>
 *
 * @author zhouxiaojian
 * @version 1.0
 * @className StationAppraiseServiceImpl
 * @date 2020/08/18 10:28
 **/
@Service
public class StationAppraiseServiceImpl implements StationAppraiseService {

    @Resource
    StationAppraiseMapper stationAppraiseMapper;

    @Override
    public int releaseComments(String stationNo, String businessNo,
                               Integer praiseDegree, String content,
                               List<String> appraiseImgs) {
        //默认五星
        if(praiseDegree == null){
            praiseDegree = 5;
        }
        //获取时间
        Date now = new Date();
        String time = DateUtil.format(now, DateUtil.FORMAT_FULL);
        StationAppraise stationAppraise = new StationAppraise(TypeConvertUtil.$int(stationNo), businessNo, praiseDegree, content, time);
        //添加站点服务评价
        int i = stationAppraiseMapper.save(stationAppraise);

        //如果有评价图片
        if(CollectionUtils.isNotEmpty(appraiseImgs)){
            //添加站点服务评价图片

        }
        return i;
    }
}
