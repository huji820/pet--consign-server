package com.jxywkj.application.pet.service.spring.admin;

import com.jxywkj.application.pet.dao.admin.ManagerMapper;
import com.jxywkj.application.pet.model.admin.Manager;
import com.jxywkj.application.pet.service.facade.admin.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description
 * @Author Administrator
 * @Date 2019-12-06 14:41
 * @Version 1.0
 */
@Service
public class ManagerServiceImpl implements ManagerService {

    @Resource
    ManagerMapper managerMapper;

    @Override
    public Manager getManager(String phone) {
        return managerMapper.getManager(phone);
    }

    @Override
    public List<Manager> listManager() {
        List<Manager> managers = managerMapper.listManager();
        return managers;
    }
}
