package com.jxywkj.application.pet.model.consign;

import lombok.Data;

import java.util.List;


/**
 * <p>
 * 站点黑名单
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className StationBlacklist
 * @date 2019/11/23 10:23
 **/
@Data
public class StationBlacklist {
    /**
     * 所属站点
     */
    Station station;

    /**
     * 创建时间
     */
    String createTime;

    /**
     * 黑名单站点
     */
    Station blacklistStation;

    /**
     * 黑名单集合
     */
    List<Station> blacklistStationList;
}
