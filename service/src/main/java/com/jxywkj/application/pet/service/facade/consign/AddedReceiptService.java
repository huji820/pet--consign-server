package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.AddedReceipt;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName AddedReceiptService
 * @Description 增值服务_上门接宠
 * @Author LiuXiangLin
 * @Date 2019/7/25 14:35
 * @Version 1.0
 **/
public interface AddedReceiptService {
    /**
     * <p>
     * 保存或者新能一个列表
     * </p>
     *
     * @param addedReceiptList 配置列表
     * @return int
     * @author LiuXiangLin
     * @date 10:36 2020/4/14
     **/
    int saveOrUpdateList(@Param("addedReceiptList") List<AddedReceipt> addedReceiptList);

    /**
     * <p>
     * 通过站点编号获取配置列表
     * </p>
     *
     * @param stationNo 站点编号
     * @return java.util.List<com.jxywkj.application.pet.model.consign.AddedReceipt>
     * @author LiuXiangLin
     * @date 10:36 2020/4/14
     **/
    List<AddedReceipt> listByStationNo(@Param("stationNo") int stationNo);
}
