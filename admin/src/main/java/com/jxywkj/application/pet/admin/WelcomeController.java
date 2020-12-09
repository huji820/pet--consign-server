package com.jxywkj.application.pet.admin;

import com.jxywkj.application.pet.service.facade.report.ReportService;
import com.yangwang.sysframework.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * @Description
 * @Author Administrator
 * @Date 2019-12-08 13:32
 * @Version 1.0
 */
@Controller
@RequestMapping("admin/welcome")
public class WelcomeController {

    @Autowired
    ReportService reportService;

    @GetMapping("")
    public ModelAndView welcome() throws Exception {
        ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());
        mv.addObject("simpleReport", reportService.getSimpleReportVO());
        mv.addObject("performances", reportService.listPerformanceVO(1));
        mv.setViewName("manager/welcome");
        return mv;
    }
}
