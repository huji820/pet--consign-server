package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.AddedSend;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 增值服务-送宠物到家
 * </p>
 *
 * @author LiuXiangLin
 * @date 9:51 2020/4/14
 **/
public interface AddedSendMapper {
    /**
     * <p>
     * 新增一个梯度
     * </p>
     *
     * @param addedSendList 送宠到家列表
     * @return int
     * @author LiuXiangLin
     * @date 9:52 2020/4/14
     **/
    int saveOrUpdateList(@Param("addedSendList") List<AddedSend> addedSendList);

    /**
     * <p>
     * 通过站点编号或者列表
     * </p>
     *
     * @param stationNo 站点编号
     * @return java.util.List<com.jxywkj.application.pet.model.consign.AddedSend>
     * @author LiuXiangLin
     * @date 9:53 2020/4/14
     **/
    List<AddedSend> listByStationNo(@Param("stationNo") int stationNo);
}
