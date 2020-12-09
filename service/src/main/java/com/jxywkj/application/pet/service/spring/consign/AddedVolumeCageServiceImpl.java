package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.dao.consign.AddedVolumeCageMapper;
import com.jxywkj.application.pet.model.consign.AddedVolumeCage;
import com.jxywkj.application.pet.model.consign.dto.AddedVolumeCageDTO;
import com.jxywkj.application.pet.service.facade.consign.AddedVolumeCageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author LiuXiangLin
 * @version 1.0
 * @className AddedVolumeCageServiceImpl
 * @date 2019/10/21 11:51
 **/
@Service
public class AddedVolumeCageServiceImpl implements AddedVolumeCageService {
    @Resource
    AddedVolumeCageMapper addedVolumeCageMapper;

    @Override
    public List<AddedVolumeCage> listByTransportNo(int transportNo,int stationNo) {
        return addedVolumeCageMapper.listByTransportNo(transportNo,stationNo);
    }

    @Override
    public int saveOrUpdate(List<AddedVolumeCageDTO> addedVolumeCageDtoList) {
        /*
         * 1、首先判断是使用什么方式，
         * 2、如果是使用按重量计算则删除这个运输路线的按体积算笼具配置
         * 3、如果是按体积计算 则向先删除再提交（更新操作）
         * */
        // 删除数据
        int result = addedVolumeCageMapper.deleteByTransportNo(addedVolumeCageDtoList.get(0).getTransport().getTransportNo());

        // 如果按体积计算则插入数据
        if (addedVolumeCageDtoList.get(0).getUseVolume()) {
            result += addedVolumeCageMapper.saveList(addedVolumeCageDtoList);
        }

        return result;
    }
}
