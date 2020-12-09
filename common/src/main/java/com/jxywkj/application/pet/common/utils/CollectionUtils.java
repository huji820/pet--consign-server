package com.jxywkj.application.pet.common.utils;

import java.util.List;

/**
 * @ClassName CollectionUtils
 * @Description 集合工具类
 * @Author LiuXiangLin
 * @Date 2019/9/16 10:15
 * @Version 1.0
 **/
public class CollectionUtils {
    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }

    public static boolean isNotEmpty(List list) {
        return !isEmpty(list);
    }
}
