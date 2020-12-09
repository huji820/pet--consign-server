package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.StationAppraise;
import com.jxywkj.application.pet.model.consign.StationBlacklist;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  站点服务评价
 * </p>
 *
 * @author zhouxiaojian
 * @version 1.0
 * @className StationAppraiseMapper
 * @date 2020/08/18 10:28
 **/
@Mapper
public interface StationAppraiseMapper {

    /**
     * 添加一条站点服务评价
     * @param stationAppraise
     * @return
     */
   int save(StationAppraise stationAppraise);
}
