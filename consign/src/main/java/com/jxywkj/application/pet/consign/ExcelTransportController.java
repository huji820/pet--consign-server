package com.jxywkj.application.pet.consign;

import com.jxywkj.application.pet.service.spring.consign.ExcelUtils;
import com.jxywkj.application.pet.model.common.ExcelTransportDO;
import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.service.facade.consign.ExcelTransportService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

/**
 * @ClassName ExcelTransportController
 * @Description Excel
 * @Author LiuXiangLin
 * @Date 2019/7/23 11:57
 * @Version 1.0
 **/
@RestController
@RequestMapping("/consign/file")
public class ExcelTransportController {

    @Resource
    ExcelUtils excelUtils;

    @Resource
    ExcelTransportService excelTransportService;


    @GetMapping("/export/{type}")
    public void exportExcel(@PathVariable("type") String type, HttpServletResponse response, @SessionAttribute("staff") Staff staff) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        // 获取Excel对象
        Workbook workbook = excelUtils.exportExcel(excelTransportService.listByStationNo(staff.getStation().getStationNo() + ""), ExcelTransportDO.class);

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
