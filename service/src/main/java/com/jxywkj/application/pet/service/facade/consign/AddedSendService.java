package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.AddedSend;

import java.util.List;

/**
 * <p>
 * 增值服务-送宠上门
 * </p>
 *
 * @author LiuXiangLin
 * @date 10:13 2020/4/14
 **/
public interface AddedSendService {
    /**
     * <p>
     * 新增一个列表
     * </p>
     *
     * @param addedSendList 增值服务列表
     * @return int
     * @author LiuXiangLin
     * @date 10:14 2020/4/14
     **/
    int saveOrUpdateList(List<AddedSend> addedSendList);

    /**
     * <p>
     * 通过站点编号获取增值服务列表
     * </p>
     *
     * @param stationNo 站点编号
     * @return java.util.List<com.jxywkj.application.pet.model.consign.AddedSend>
     * @author LiuXiangLin
     * @date 10:14 2020/4/14
     **/
    List<AddedSend> listByStationNo(int stationNo);
}
