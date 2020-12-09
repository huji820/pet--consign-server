package com.jxywkj.application.pet.dao.report;

import com.jxywkj.application.pet.model.vo.report.HomePerformanceVO;
import com.jxywkj.application.pet.model.vo.report.HomeSimpleReportVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RepoerMapper {

    HomeSimpleReportVO getSimpleReportVO();

    /**
     * 获取一周内的销售简报
     * @return
     */
    List<HomePerformanceVO> listPerformanceVO(@Param("dayType") int dayType);
}
