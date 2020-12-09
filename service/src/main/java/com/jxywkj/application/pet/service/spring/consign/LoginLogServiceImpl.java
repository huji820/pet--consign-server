package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.dao.consign.LoginLogMapper;
import com.jxywkj.application.pet.model.consign.LoginLog;
import com.jxywkj.application.pet.service.facade.consign.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author Administrator
 * @Date 2019-12-25 19:48
 * @Version 1.0
 */
@Service
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    LoginLogMapper loginLogMapper;

    @Override
    public int insertLoginLog(LoginLog loginLog) {
        return loginLogMapper.insertLoginLog(loginLog);
    }
}
