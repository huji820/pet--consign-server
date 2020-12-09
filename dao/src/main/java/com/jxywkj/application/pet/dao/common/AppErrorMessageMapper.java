package com.jxywkj.application.pet.dao.common;

import com.jxywkj.application.pet.model.common.AppErrorMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName AppErrorMessageMapper
 * @Description 错误日志
 * @Author LiuXiangLin
 * @Date 2019/9/30 10:08
 * @Version 1.0
 **/
@Mapper
public interface AppErrorMessageMapper {
    /**
     * @Author LiuXiangLin
     * @Description 保存
     * @Date 10:10 2019/9/30
     * @Param [appErrorMessage]
     * @return int
     **/
    int save(@Param("appErrorMessage") AppErrorMessage appErrorMessage);
}
