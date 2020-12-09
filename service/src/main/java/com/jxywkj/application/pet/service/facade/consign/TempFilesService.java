package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.file.OrderStateTempFiles;

import java.util.List;

/**
 * @ClassName TempFilesService
 * @Description 上传临时文件
 * @Author LiuXiangLin
 * @Date 2019/8/13 11:15
 * @Version 1.0
 **/
public interface TempFilesService {
    /**
     * @Author LiuXiangLin
     * @Description 添加一条临时文件
     * @Date 11:16 2019/8/13
     * @Param [orderStateTempFiles]
     * @return int
     **/
    int addATempFiles(OrderStateTempFiles orderStateTempFiles);

    /**
     * @Author LiuXiangLin
     * @Description 通过单号查询所有数据
     * @Date 11:36 2019/8/13
     * @Param [orderNo]
     * @return java.util.List<com.jxywkj.application.pet.model.consign.file.OrderStateTempFiles>
     **/
    List<OrderStateTempFiles> listByOrderNo(String orderNo);

    /**
     * @Author LiuXiangLin
     * @Description 通过订单号删除数据
     * @Date 14:36 2019/8/13
     * @Param [orderNo]
     * @return int
     **/
    int deleteByOrderNo(String orderNo);
}
