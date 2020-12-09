package com.jxywkj.application.pet.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author LiuXiangLin
 * @Description Excel自定义注解
 * @Date 9:34 2019/7/23
 * @Param
 * @return
 **/

@Target({ElementType.FIELD})// 作用于方法或者变量
@Retention(RetentionPolicy.RUNTIME)// JVM加载之后注解任然存在
public @interface Excel {
    // 对应标题名称
    String name();

    // 存在表格的角标 默认值为0
    int index() default 0;

    // 单元格宽度 默认4000
    int width() default 4000;
}
