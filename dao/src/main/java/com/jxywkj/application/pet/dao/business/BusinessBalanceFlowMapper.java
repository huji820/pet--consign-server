package com.jxywkj.application.pet.dao.business;

import com.jxywkj.application.pet.model.business.BusinessBalanceFlow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className BusinessBalanceFlowMapper
 * @date 2019/12/30 10:41
 **/
@Mapper
public interface BusinessBalanceFlowMapper {
    /**
     * <p>
     * 新增一条流水
     * </p>
     *
     * @param businessBalanceFlow 流水对象
     * @return int
     * @author LiuXiangLin
     * @date 10:50 2019/12/30
     **/
    int save(@Param("businessBalanceFlow") BusinessBalanceFlow businessBalanceFlow);

    /**
     * <p>
     * 通过站点编号查询数据
     * </p>
     *
     * @param businessNo 站点编号
     * @param offset     排除条数
     * @param limit      显示条数（如果为0 则表示不做分页）
     * @return java.util.List<com.jxywkj.application.pet.model.business.BusinessBalanceFlow>
     * @author LiuXiangLin
     * @date 10:51 2019/12/30
     **/
    List<BusinessBalanceFlow> listByStationNo(@Param("businessNo") String businessNo,
                                              @Param("offset") int offset,
                                              @Param("limit") int limit);
}
