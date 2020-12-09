package com.jxywkj.application.pet.common.utils;

import java.math.BigDecimal;

/**
 * BigDecimal 工具类
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className BigDecimalUtils
 * @date 2019/10/25 17:12
 **/
public class BigDecimalUtils {
    /**
     * 两个数字是否相等
     *
     * @param numberOne 数字一
     * @param numberTwo 数字二
     * @return boolean
     * @author LiuXiangLin
     * @date 17:15 2019/10/25
     **/
    public static boolean equal(BigDecimal numberOne, BigDecimal numberTwo) {
        return numberOne.compareTo(numberTwo) == 0;
    }
}
