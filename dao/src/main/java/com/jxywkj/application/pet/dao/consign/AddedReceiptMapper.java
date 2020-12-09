package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.AddedReceipt;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 增值服务-上门接宠
 * </p>
 *
 * @author LiuXiangLin
 * @date 10:20 2020/4/14
 **/
public interface AddedReceiptMapper {
    /**
     * <p>
     * 新增或者保存一条数据
     * </p>
     *
     * @param addedReceiptList 上门接宠列表
     * @return int
     * @author LiuXiangLin
     * @date 10:21 2020/4/14
     **/
    int saveOrUpdateList(@Param("addedReceiptList") List<AddedReceipt> addedReceiptList);

    /**
     * <p>
     * 通过站点编号获取接宠配置列表
     * </p>
     *
     * @param stationNo 站点编号
     * @return java.util.List<com.jxywkj.application.pet.model.consign.AddedReceipt>
     * @author LiuXiangLin
     * @date 10:24 2020/4/14
     **/
    List<AddedReceipt> listByStationNo(@Param("stationNo") int stationNo);
}
