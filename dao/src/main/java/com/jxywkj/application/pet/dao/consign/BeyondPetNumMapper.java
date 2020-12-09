package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.BeyondPetNum;
import com.jxywkj.application.pet.model.consign.Station;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 超出一只添加的金额
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className BeyondPetNumMapper
 * @date 2019/11/22 11:39
 **/
@Mapper
public interface BeyondPetNumMapper {
    /**
     * <p>
     * 通过站点编号和运输类型查询数据
     * </p>
     *
     * @param stationNo 站点编号
     * @param type      运输类型
     * @return com.jxywkj.application.pet.model.consign.BeyondPetNum
     * @author LiuXiangLin
     * @date 11:40 2019/11/22
     **/
    BeyondPetNum getByStationNoAndType(@Param("stationNo") int stationNo, @Param("transportType") int type);

    /**
     * <p>
     * 通过站点编号查询配置
     * </p>
     *
     * @param stationNo 站点编号
     * @return java.util.List<com.jxywkj.application.pet.model.consign.BeyondPetNum>
     * @author LiuXiangLin
     * @date 11:41 2019/11/22
     **/
    List<BeyondPetNum> listByStationNo(@Param("stationNo") int stationNo);

    /**
     * <p>
     * 通过运输路线查询配置
     * </p>
     *
     * @param transportNo 运输路线编号
     * @return com.jxywkj.application.pet.model.consign.BeyondPetNum
     * @author LiuXiangLin
     * @date 11:43 2019/11/22
     **/
    BeyondPetNum getByTransportNo(@Param("station") Station station, @Param("transportNo") int transportNo);

    /**
     * <p>
     * 更新一条数据
     * </p>
     *
     * @param beyondPetNum 数据对象
     * @return int
     * @author LiuXiangLin
     * @date 11:44 2019/11/22
     **/
    int saveOrUpdate(@Param("beyondPetNum") BeyondPetNum beyondPetNum);

    /**
     * <p>
     * 更新一个站点的所有类型
     * </p>
     *
     * @param beyondPetNumList 数据集合
     * @return int
     * @author LiuXiangLin
     * @date 11:45 2019/11/22
     **/
    int saveOrUpdateByStation(@Param("beyondPetNumList") List<BeyondPetNum> beyondPetNumList);

    /**
     * <p>
     * 通过站点编号删除数据
     * </p>
     *
     * @param stationNo 站点编号
     * @return int
     * @author LiuXiangLin
     * @date 11:46 2019/11/22
     **/
    int deleteByStationNo(@Param("stationNo") int stationNo);
}
