package com.jxywkj.application.pet.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author LiuXiangLin
 * @version 1.0
 * @className ApplicationContextProvider
 * @description
 * @date 2019/8/27 17:48
 **/
@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext context;

    private ApplicationContextUtils() {
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static <T> T getBean(Class<T> aClass) {
        return context.getBean(aClass);
    }
}
