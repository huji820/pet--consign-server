package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.Position;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName PositionMapper
 * @Description 机场Mapper
 * @Author LiuXiangLin
 * @Date 2019/7/24 14:57
 * @Version 1.0
 **/
public interface PositionMapper {
    /**
     * <p>
     * 新增或者更新数据
     * </p>
     *
     * @param position 位置对象
     * @return int
     * @author LiuXiangLin
     * @date 17:38 2020/4/10
     **/
    int saveOrUpdate(@Param("position") Position position);

    /**
     * <p>
     * 通过站点编号以及类型查询运输路线
     * </p>
     *
     * @param stationNo 站点编号
     * @param type      类型
     * @return com.jxywkj.application.pet.model.consign.BusinessPosition
     * @author LiuXiangLin
     * @date 17:38 2020/4/10
     **/
    Position getByStationNoAndType(@Param("stationNo") int stationNo, @Param("type") int type);
}
