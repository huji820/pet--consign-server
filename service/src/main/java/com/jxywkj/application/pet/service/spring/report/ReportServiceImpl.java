package com.jxywkj.application.pet.service.spring.report;

import com.jxywkj.application.pet.dao.report.RepoerMapper;
import com.jxywkj.application.pet.model.vo.report.HomePerformanceVO;
import com.jxywkj.application.pet.model.vo.report.HomeSimpleReportVO;
import com.jxywkj.application.pet.service.facade.report.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author Administrator
 * @Date 2019-12-08 13:53
 * @Version 1.0
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    RepoerMapper repoerMapper;

    @Override
    public HomeSimpleReportVO getSimpleReportVO() {
        return repoerMapper.getSimpleReportVO();
    }

    @Override
    public List<HomePerformanceVO> listPerformanceVO(int dayType) {
        return repoerMapper.listPerformanceVO(dayType);
    }
}
