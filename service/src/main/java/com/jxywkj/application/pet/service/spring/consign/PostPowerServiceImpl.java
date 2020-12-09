package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.dao.consign.PostPowerMapper;
import com.jxywkj.application.pet.model.common.PostPower;
import com.jxywkj.application.pet.service.facade.consign.PostPowerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName PostPowerServiceImpl
 * @Description
 * @Author LiuXiangLin
 * @Date 2019/9/25 11:30
 * @Version 1.0
 **/
@Service
public class PostPowerServiceImpl implements PostPowerService {

    @Resource
    PostPowerMapper postPowerMapper;

    @Override
    public int saveOrUpdate(PostPower postPower) {
        return postPowerMapper.saveOrUpdate(postPower);
    }

    @Override
    public List<PostPower> listByPostNo(int postNo) {
        return postPowerMapper.listByPostNo(postNo);
    }

    @Override
    public List<String> listFieldByPostNo(int postNo) {
        return postPowerMapper.listFieldsByPostNo(postNo);
    }
}
