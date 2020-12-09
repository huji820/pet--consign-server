package com.jxywkj.application.pet.service.spring.common;

import com.jxywkj.application.pet.dao.common.AppErrorMessageMapper;
import com.jxywkj.application.pet.model.common.AppErrorMessage;
import com.jxywkj.application.pet.service.facade.common.AppErrorMessageService;
import com.yangwang.sysframework.utils.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @ClassName AppErrorMessageServiceImpl
 * @Description
 * @Author LiuXiangLin
 * @Date 2019/9/30 10:16
 * @Version 1.0
 **/
@Service
public class AppErrorMessageServiceImpl implements AppErrorMessageService {
    @Resource
    AppErrorMessageMapper appErrorMessageMapper;

    @Override
    public int save(AppErrorMessage appErrorMessage) {
        appErrorMessage.setDateTime(DateUtil.format(new Date(), DateUtil.FORMAT_FULL));
        return appErrorMessageMapper.save(appErrorMessage);
    }
}
