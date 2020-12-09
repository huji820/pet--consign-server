package com.jxywkj.application.pet.common.utils;

import com.yangwang.sysframework.utils.DateUtil;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className DateUtilsExtend
 * @date 2019/11/21 16:14
 **/
public class DateUtilsExtend extends DateUtil {
    /**
     * <p>
     * 获取时间加30分钟后的时间
     * </p>
     *
     * @param dt   时间对象
     * @param mins 分钟数
     * @return java.util.Date
     * @author LiuXiangLin
     * @date 16:15 2019/11/21
     **/
    public static Date getAddMinDate(Date dt, int mins) {
        if (dt == null) {
            dt = new Date();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.add(Calendar.MINUTE, mins);
        return cal.getTime();
    }
}
