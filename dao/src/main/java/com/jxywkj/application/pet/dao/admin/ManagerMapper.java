package com.jxywkj.application.pet.dao.admin;

import com.jxywkj.application.pet.model.admin.Manager;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ManagerMapper {

    Manager getManager(String phone);

    /**
     * @Description: 获取所有管理员
     * @Author: zxj
     * @Date: 2020/10/27 15:41
     * @return: java.util.List<com.jxywkj.application.pet.model.admin.Manager>
     **/
    List<Manager> listManager();
}
