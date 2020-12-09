package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.LoginLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginLogMapper {

    /**
     * 新增一条登录记录
     * @param loginLog
     * @return
     */
    int insertLoginLog(@Param("loginLog") LoginLog loginLog);
}
