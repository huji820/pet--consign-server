package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.AddedVolumeCage;
import com.jxywkj.application.pet.model.consign.dto.AddedVolumeCageDTO;

import java.util.List;

/**
 * @author LiuXiangLin
 * @version 1.0
 * @className AddedVolumeCageService
 * @date 2019/10/21 11:51
 **/
public interface AddedVolumeCageService {
    /**
     * 通过运输路线查询笼具配置数据
     *
     * @param transportNo 运输路线
     * @return java.util.List<com.jxywkj.application.pet.model.consign.AddedWeightCage>
     * @author LiuXiangLin
     * @date 11:47 2019/10/21
     **/
    List<AddedVolumeCage> listByTransportNo(int transportNo,int stationNo);

    /**
     * 更新或者新增数据
     *
     * @param addedVolumeCageDtsList 需要保存的数据
     * @return int
     * @author LiuXiangLin
     * @date 16:16 2019/10/21
     **/
    int saveOrUpdate(List<AddedVolumeCageDTO> addedVolumeCageDtsList);
}
