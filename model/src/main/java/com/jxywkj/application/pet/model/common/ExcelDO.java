package com.jxywkj.application.pet.model.common;

import lombok.Data;

/**
 * @ClassName ExcelDO
 * @Description 表格数据对象
 * @Author LiuXiangLin
 * @Date 2019/7/23 10:07
 * @Version 1.0
 **/
@Data
public class ExcelDO {
    /**
     * 角标
     */
    Integer index;
    /**
     * 名称
     */
    String name;
    /**
     * 键
     */
    String key;
    /**
     * 宽度
     */
    Integer width;

    public ExcelDO() {
    }

    public ExcelDO(int index, String name, String key, int width) {
        this.index = index;
        this.name = name;
        this.key = key;
        this.width = width;
    }
}
