package com.jxywkj.application.pet.common.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * bean工具类
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className BeanUtils
 * @date 2020/3/27 9:42
 **/
public class BeanUtils {
    /**
     * <p>
     * 对象转map
     * </p>
     *
     * @param obj 对象
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author LiuXiangLin
     * @date 9:43 2020/3/27
     **/
    public static Map<String, Object> beanToMap(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性
                if (!"class".equals(key)) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);

                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            System.out.println("transBean2Map Error " + e);
        }
        return map;
    }

}
