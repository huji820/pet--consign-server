package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.AddedWeightCage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author LiuXiangLin
 * @version 1.0
 * @className AddedWeightCageMapper
 * @description 按重量计算笼具
 * @date 2019/10/19 9:53
 **/
@Mapper
public interface AddedWeightCageMapper {
    /**
     * 保存或者新增数据
     *
     * @param addedWeightCages 梯度
     * @return int 新增条数
     * @author LiuXiangLin
     * @description 保存或者新增数据
     * @date 9:58 2019/10/19
     **/
    int saveList(@Param("addedWeightCages") List<AddedWeightCage> addedWeightCages);

    /**
     * 通过站点编号查询梯度
     *
     * @param stationNo     站点编号
     * @param transportType 运输方式
     * @return java.util.List<com.jxywkj.application.pet.model.consign.AddedWeightCage> 笼具集合
     * @author LiuXiangLin
     * @date 9:58 2019/10/19
     **/
    List<AddedWeightCage> listByStationNoAndType(@Param("stationNo") int stationNo, @Param("transportType") int transportType);

    /**
     * 通过站点编号以及运输方式删除数据
     *
     * @param stationNo     站点编号
     * @param transportType 运输类型
     * @return int 修改条数
     * @author LiuXiangLin
     * @date 13:58 2019/10/19
     **/
    int deleteByStationNoAndType(@Param("stationNo") int stationNo, @Param("transportType") int transportType);

    /**
     * 通过站点编号以及运输类型查询运输重量的最大值
     *
     * @param transportNo   运输路线
     * @param usable        可用状态
     * @param transportType 运输类型
     * @return java.math.BigDecimal
     * @author LiuXiangLin
     * @date 15:21 2019/10/19
     **/
    BigDecimal getMaxWeightByStationNoAndType(@Param("transportNo") int transportNo,
                                              @Param("transportType") int transportType,
                                              @Param("usable") int usable);
}
