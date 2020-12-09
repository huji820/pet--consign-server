package com.jxywkj.application.pet.consign;

import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.model.consign.StationBalanceFlow;
import com.jxywkj.application.pet.model.consign.vo.StationBalanceFlowVo;
import com.jxywkj.application.pet.service.facade.consign.StationBalanceFlowService;
import com.jxywkj.application.pet.service.spring.consign.ExcelUtils;
import com.yangwang.sysframework.utils.Pagination;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @Description
 * @Author Administrator
 * @Date 2019-12-28 21:41
 * @Version 1.0
 */
@RestController
@RequestMapping("consign/flow/station")
public class StationBalanceFlowController {

    @Autowired
    StationBalanceFlowService stationBalanceFlowService;

    @Resource
    ExcelUtils excelUtils;

    @GetMapping("")
    public Pagination listStationFlow(String startDate, String endDate, int pageIndex, int pageSize, @SessionAttribute("staff") Staff staff) {
        Pagination pagination = new Pagination(pageIndex -1, pageSize);
        pagination.setRoot(stationBalanceFlowService.listStationFlow(staff.getStation(), startDate, endDate, pagination.getStart(), pageSize));
        pagination.setTotalCount(stationBalanceFlowService.countStationFlow(staff.getStation(), startDate, endDate));
        return pagination;
    }

    @GetMapping("/export")
    public void exportExcel(String startDate, String endDate, HttpServletResponse response, @SessionAttribute("staff") Staff staff) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        // 获取Excel对象
        List<StationBalanceFlowVo> list = stationBalanceFlowService.listStationFlow(staff.getStation(), startDate, endDate, 1, 9999);
        Workbook workbook = excelUtils.exportExcel(list, StationBalanceFlow.class);

        // 设置文件名称
        String filename = System.currentTimeMillis() + ".xls";
        // 设置相应类型
        response.setContentType("application/vnd.ms-excel");
        // 设置头部信息
        response.setHeader("Content-disposition", "attachment;filename=" + filename);
        // 将文件作为输出流输出
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            workbook.write(os);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
