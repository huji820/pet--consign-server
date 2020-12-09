package com.jxywkj.application.pet.service.facade.report;

import com.jxywkj.application.pet.model.vo.report.HomePerformanceVO;
import com.jxywkj.application.pet.model.vo.report.HomeSimpleReportVO;

import java.util.List;

public interface ReportService {

    /**
     * 获取首页简报
     * @return
     */
    HomeSimpleReportVO getSimpleReportVO();

    /**
     * 获取一周内的销售简报
     * @return
     */
    List<HomePerformanceVO> listPerformanceVO(int dayType);
}
