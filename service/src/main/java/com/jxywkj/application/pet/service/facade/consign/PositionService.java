package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.Position;

/**
 * @ClassName PositionService
 * @Description 机场
 * @Author LiuXiangLin
 * @Date 2019/7/26 11:31
 * @Version 1.0
 **/
public interface PositionService {
    /**
     * <p>
     * 新增或者修改
     * </p>
     *
     * @param position 位置对象
     * @return int
     * @author LiuXiangLin
     * @date 17:42 2020/4/10
     **/
    int saveOrUpdate(Position position);

    /**
     * <p>
     * 通过站点编号以及类型查询位置信息
     * </p>
     *
     * @param stationNo 站点编号
     * @param type      类型
     * @return com.jxywkj.application.pet.model.consign.BusinessPosition
     * @author LiuXiangLin
     * @date 17:43 2020/4/10
     **/
    Position getByStationNoAndType(int stationNo, int type);
}
