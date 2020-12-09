package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.file.OrderStateTempFiles;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName TempFilesMapper
 * @Description 临时文件上传·
 * @Author LiuXiangLin
 * @Date 2019/8/13 11:03
 * @Version 1.0
 **/
@Component
public interface TempFilesMapper {
    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 添加一条数据
     * @Date 11:04 2019/8/13
     * @Param [orderStateTempFiles]
     **/
    int addATempFiles(@Param("orderStateTempFiles") OrderStateTempFiles orderStateTempFiles);


    /**
     * @return java.util.List<com.jxywkj.application.pet.model.consign.file.OrderStateTempFiles>
     * @Author LiuXiangLin
     * @Description 通过单号查询列表
     * @Date 11:37 2019/8/13
     * @Param [orderNo]
     **/
    List<OrderStateTempFiles> listByOrderNo(@Param("orderNo") String orderNo);


    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 通过单号删除数据
     * @Date 14:37 2019/8/13
     * @Param [orderNo]
     **/
    int deleteByOrderNo(@Param("orderNo") String orderNo);
}
