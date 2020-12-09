package com.jxywkj.application.pet.service.facade.common;

import com.jxywkj.application.pet.model.common.AppErrorMessage;

/**
 * @ClassName AppErrorMessageService
 * @Description app错误日志
 * @Author LiuXiangLin
 * @Date 2019/9/30 10:16
 * @Version 1.0
 **/
public interface AppErrorMessageService {
    int save(AppErrorMessage appErrorMessage);
}
