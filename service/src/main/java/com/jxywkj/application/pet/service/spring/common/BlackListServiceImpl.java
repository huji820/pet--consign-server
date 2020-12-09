package com.jxywkj.application.pet.service.spring.common;

import com.jxywkj.application.pet.dao.common.BlackListMapper;
import com.jxywkj.application.pet.model.common.BlackList;
import com.jxywkj.application.pet.service.facade.common.BlackListService;
import com.yangwang.sysframework.utils.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 黑名单
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className BlackListServiceImpl
 * @date 2020/1/8 9:25
 **/
@Service
public class BlackListServiceImpl implements BlackListService {
    @Resource
    BlackListMapper blackListMapper;

    @Override
    public int saveList(List<String> phoneList) {
        List<BlackList> blackLists = new ArrayList<>(phoneList.size());
        String nowDate = DateUtil.formatFull(new Date());
        for (String phone : phoneList) {
            blackLists.add(new BlackList(phone, nowDate));
        }
        return blackListMapper.save(blackLists);
    }

    @Override
    public List<BlackList> listAll(int offset, int limit) {
        return blackListMapper.listAll(offset, limit);
    }

    @Override
    public int deleteByPhone(String phone) {
        return blackListMapper.deleteByPhone(phone);
    }

    @Override
    public BlackList getByPhone(String phone) {
        return null;
    }
}
