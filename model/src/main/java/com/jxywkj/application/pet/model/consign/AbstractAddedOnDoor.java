package com.jxywkj.application.pet.model.consign;

import com.alibaba.druid.support.spring.stat.annotation.Stat;
import com.jxywkj.application.pet.common.utils.CollectionUtils;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className AbstractAddedOnDoor
 * @date 2020/4/14 9:44
 **/
@Data
public abstract class AbstractAddedOnDoor implements Comparable<AbstractAddedOnDoor> {
    /**
     * 开始距离
     */
    BigDecimal startDistance;
    /**
     * 结束距离
     */
    BigDecimal endDistance;
    /**
     * 单价
     */
    BigDecimal price;
    /**
     * 所属站点
     */
    Station station;

    public static void addSetStation(List<? extends AbstractAddedOnDoor> abstractAddedOnDoorList, Station station) {
        if (!CollectionUtils.isEmpty(abstractAddedOnDoorList)) {
            for (AbstractAddedOnDoor abstractAddedOnDoor : abstractAddedOnDoorList) {
                abstractAddedOnDoor.station = station;
            }
        }
    }

    @Override
    public int compareTo(AbstractAddedOnDoor o) {
        return getEndDistance().compareTo(o.getEndDistance());
    }
}
