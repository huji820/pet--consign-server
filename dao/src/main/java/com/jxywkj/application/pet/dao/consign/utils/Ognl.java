package com.jxywkj.application.pet.dao.consign.utils;

import com.yangwang.sysframework.utils.StringUtil;

public class Ognl {
    /**
     * 可以用于判断是否为空
     *
     * @param o
     * @return
     */
    @SuppressWarnings("unchecked")
    public static boolean isEmpty(Object o) throws IllegalArgumentException {
        return !StringUtil.isNotNull(o);
    }

    /**
     * 可以用于判断是否为空
     *
     * @param o
     * @return
     */
    @SuppressWarnings("unchecked")
    public static boolean isNotEmpty(Object o) throws IllegalArgumentException {
        return StringUtil.isNotNull(o);
    }
}
