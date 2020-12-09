package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.StationBlacklist;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 站点黑名单
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className StationBlackListService
 * @date 2019/11/23 10:49
 **/
public interface StationBlackListService {
    /**
     * <p>
     * 新增或者修改一条数据
     * </p>
     *
     * @param stationBlacklist 站点黑名单对象
     * @return int
     * @author LiuXiangLin
     * @date 10:30 2019/11/23
     **/
    int saveOrUpdate(@Param("stationBlacklist") StationBlacklist stationBlacklist);

    /**
     * <p>
     * 删除一条黑名单记录
     * </p>
     *
     * @param blackStationNo 被拉黑站点编号
     * @param stationNo      站点编号
     * @return int
     * @author LiuXiangLin
     * @date 10:31 2019/11/23
     **/
    int deleteByBlackStationNo(@Param("stationNo") int stationNo, @Param("blackStationNo") int blackStationNo);

    /**
     * <p>
     * 删除一个站点的所有黑名单
     * </p>
     *
     * @param stationNo 站点编号
     * @return int
     * @author LiuXiangLin
     * @date 10:32 2019/11/23
     **/
    int deleteByStationNo(@Param("stationNo") int stationNo);

    /**
     * <p>
     * 查询该站点是否被拉黑
     * 如果返回了一个非空对象
     * 则表示该站点被拉黑
     * </p>
     *
     * @param stationNo      主站点编号
     * @param checkStationNo 需要检查的站点编号
     * @return com.jxywkj.application.pet.model.consign.StationBlacklist
     * @author LiuXiangLin
     * @date 10:33 2019/11/23
     **/
    StationBlacklist getByBlackStationNo(@Param("stationNo") int stationNo, @Param("checkStationNo") int checkStationNo);

    /**
     * <p>
     * 查询站点的所有黑名单站点
     * </p>
     *
     * @param stationNo 站点编号
     * @return com.jxywkj.application.pet.model.consign.StationBlacklist
     * @author LiuXiangLin
     * @date 10:37 2019/11/23
     **/
    StationBlacklist listByStationNo(@Param("stationNo") int stationNo);
}
