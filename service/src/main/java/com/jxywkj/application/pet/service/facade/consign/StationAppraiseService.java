package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.StationAppraise;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 *  站点服务评价
 * </p>
 *
 * @author zhouxiaojian
 * @version 1.0
 * @className StationAppraiseService
 * @date 2020/08/18 10:28
 **/
public interface StationAppraiseService {

    /**
     * 发布一条站点评价
     * @param stationNo   被评价的站点外键
     * @param businessNo    评价人外键
     * @param praiseDegree     评价星级
     * @param content        评价内容
     * @param appraiseImgs        评价图片路径集合
     * @return
     */
   int releaseComments(String stationNo,String businessNo,
                       Integer praiseDegree, String content,
                       List<String> appraiseImgs);
}
