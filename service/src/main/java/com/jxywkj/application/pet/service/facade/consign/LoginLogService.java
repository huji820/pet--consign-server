package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.LoginLog;

public interface LoginLogService {

    /**
     * 新增一条登录记录
     * @param loginLog
     * @return
     */
    int insertLoginLog(LoginLog loginLog);
}
