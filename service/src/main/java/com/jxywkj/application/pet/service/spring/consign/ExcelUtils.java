package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.common.annotation.Excel;
import com.jxywkj.application.pet.model.common.ExcelDO;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Component;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @ClassName ExcelUtils
 * @Description Excel工具类
 * @Author LiuXiangLin
 * @Date 2019/7/23 9:38
 * @Version 1.0
 **/
@Component
public class ExcelUtils {
    /**
     * @return org.apache.poi.ss.usermodel.Workbook
     * @Author LiuXiangLin
     * @Description 导出出Excel
     * @Date 9:41 2019/7/23
     * @Param [data, DOClass]
     **/
    public Workbook exportExcel(List<?> data, Class<?> DOClass) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        /*使用流程
         * 1、创建工作簿 WorkBook
         * 2、创建Sheet
         * 3、创建Row
         * 4、创建单元格Cell
         *
         * 使用方式
         * 使用XSSFWorkbook对象 优势是可以操作大量的数据 劣势是可能会OOM（out of memory）
         *
         * */
        /*创建工作簿*/
        // 创建单单元格对象
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 获取设置并获取单元格样式
        XSSFCellStyle xssfCellStyle = setCellStyle(workbook);

        /*创建Sheet*/
        //创建一个Sheet
        XSSFSheet sheet = workbook.createSheet();
        // 获取表格标题对象列表
        List<ExcelDO> excelDOList = getAnnotationData(DOClass);
        // 设置表格头部
        setTableHead(workbook, sheet, excelDOList);

        // 创建表格内容
        setTableBody(sheet, data, excelDOList, xssfCellStyle);

        return workbook;
    }

    /**
     * @return org.apache.poi.ss.usermodel.Workbook
     * @Author LiuXiangLin
     * @Description 设置单元格数据
     * @Date 10:47 2019/7/23
     * @Param [workbook, sheet, data, tableHeadList]
     **/
    private void setTableBody(XSSFSheet sheet, List<?> data, List<ExcelDO> tableHeadList, XSSFCellStyle xssfCellStyle) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        for (int i = 0; i < data.size(); i++) {
            // 创建一个行对象 第一行是标题
            XSSFRow nextRow = sheet.createRow(i + 1);
            // 获取数据对象的全部成员变量
            Field[] fields = data.get(i).getClass().getDeclaredFields();
            // 遍历表表格头部创建单元格对象
            for (int j = 0; j < tableHeadList.size(); j++) {
                // 创建单元格
                XSSFCell cell = nextRow.createCell(j);
                // 遍历遍历所有对象的所有成员变量
                for (Field field : fields) {
                    // 如果变量的名称和设置的表格头key相同创建一个数据
                    if (tableHeadList.get(j).getKey().equals(field.getName())) {
                        String invoke = getMethodString(data, i, field);
                        // 设置单元格的值
                        cell.setCellValue(invoke);
                        // 设置单元格样式
                        cell.setCellStyle(xssfCellStyle);
                        break;
                    }
                }
            }
        }
    }

    private String getMethodString(List<?> data, int i, Field field) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        PropertyDescriptor pd = new PropertyDescriptor(field.getName(), data.get(i).getClass());
        Method method = pd.getReadMethod();
        Object methodValue = method.invoke(data.get(i));
        if (methodValue != null) {
            return methodValue.toString();
        }
        return "";
    }


    /**
     * @return org.apache.poi.xssf.usermodel.XSSFCellStyle
     * @Author LiuXiangLin
     * @Description 设置单元格样式
     * @Date 9:56 2019/7/23
     * @Param [workbook]
     **/
    private static XSSFCellStyle setCellStyle(XSSFWorkbook workbook) {
        /*Create a new Cell style and add it to the workbook's style table
         * 创建一个新的单元格样式 并且设置到表格中
         * */
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        /*Returns the instance of DataFormat for this workbook.
         * 返回DataFormat实例的接口对象
         * */
        XSSFDataFormat format = workbook.createDataFormat();
        /*设置单元格格式化数据*/
        cellStyle.setDataFormat(format.getFormat("@"));
        return cellStyle;
    }


    /**
     * @return java.util.List<com.jxywkj.application.pet.model.common.ExcelDO>
     * @Author LiuXiangLin
     * @Description 获取注解数据
     * @Date 10:09 2019/7/23
     * @Param [pojoClass]
     **/
    private List<ExcelDO> getAnnotationData(Class pojoClass) {
        // 创建一个表格标题对象集合
        List<ExcelDO> excelPojoList = new ArrayList<>();
        // 获取该对象所有的成员变量
        Field[] declaredFields = pojoClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            // 判断变量的注解是否为Excel
            if (declaredField.isAnnotationPresent(Excel.class)) {// 是Excel注解
                // 获取该注解对象
                Excel annotation = declaredField.getAnnotation(Excel.class);
                // 通过注解的数据创建一个表格标题对象
                excelPojoList.add(new ExcelDO(annotation.index(), annotation.name(), declaredField.getName(), annotation.width()));
            }
        }
        // 通过Index进行排序排序
        excelPojoList.sort(Comparator.comparingInt(ExcelDO::getIndex));

        // 返回获取的数据
        return excelPojoList;
    }


    /**
     * @return void
     * @Author LiuXiangLin
     * @Description 设置Sheet标题
     * @Date 10:18 2019/7/23
     * @Param [workbook, sheet, excelPojoList]
     **/
    public static void setTableHead(XSSFWorkbook workbook, XSSFSheet sheet, List<ExcelDO> excelPojoList) {
        // 创建并且设置单元格样式
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        // 创建字体对象
        XSSFFont font = workbook.createFont();
        // 设置字体为加粗
        font.setBold(true);
        // 设置单元格数据对齐方式为水平居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 设置字体样式
        cellStyle.setFont(font);
        // 获取第一行的数据对象
        XSSFRow row = sheet.createRow(0);
        XSSFCell tileCell;
        /*设置表头数据*/
        for (int i = 0; i < excelPojoList.size(); i++) {
            // 创建一个表头对象
            tileCell = row.createCell(i);
            // 设置名称
            tileCell.setCellValue(excelPojoList.get(i).getName());
            // 设置样式
            tileCell.setCellStyle(cellStyle);
            // 设置表格宽度
            sheet.setColumnWidth(i, excelPojoList.get(i).getWidth());
        }
    }

}
