package com.jxywkj.application.pet.service.facade.admin;

import com.jxywkj.application.pet.model.admin.Manager;

import java.util.List;

public interface ManagerService {

    /**
     * 根据手机号码查找管理员
     * @param phone
     * @return
     */
    Manager getManager(String phone);

    /**
     * @Description: 获取所有管理员
     * @Author: zxj
     * @Date: 2020/10/27 15:42
     * @return: java.util.List<com.jxywkj.application.pet.model.admin.Manager>
     **/
    List<Manager> listManager();
}
