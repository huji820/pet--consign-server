package com.jxywkj.application.pet.dao.business;

import com.jxywkj.application.pet.model.business.BusinessWithdraw;
import com.jxywkj.application.pet.model.consign.StationWithdraw;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商家提现
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className BusinessWithdrawMapper
 * @date 2019/12/5 15:23
 **/
@Mapper
public interface BusinessWithdrawMapper {
    /**
     * <p>
     * 新增一条提现申请
     * </p>
     *
     * @param businessWithdraw 提现对象
     * @return int
     * @author LiuXiangLin
     * @date 15:24 2019/12/5
     **/
    int save(@Param("businessWithdraw") BusinessWithdraw businessWithdraw);

    /**
     * <p>
     * 更新订单状态
     * </p>
     *
     * @param withdrawNo 提现编号
     * @param state      状态
     * @return int
     * @author LiuXiangLin
     * @date 15:25 2019/12/5
     **/
    int updateState(@Param("withdrawNo") String withdrawNo, @Param("state") String state);

    /**
     * <p>
     * 通过单号获取提现单
     * </p>
     *
     * @param withdrawNo 提现单号
     * @return com.jxywkj.application.pet.model.business.BusinessWithdraw
     * @author LiuXiangLin
     * @date 17:18 2019/12/5
     **/
    BusinessWithdraw getByWithdrawNo(@Param("withdrawNo") String withdrawNo);

    /**
     * <p>
     * 通过商家编号查询提现流水
     * </p>
     *
     * @param businessNo 商家编号
     * @param offset     排除条数
     * @param limit      显示条数
     * @return java.util.List<com.jxywkj.application.pet.model.business.BusinessWithdraw>
     * @author LiuXiangLin
     * @date 10:11 2019/12/6
     **/
    List<BusinessWithdraw> listByBusinessNo(@Param("businessNo") String businessNo, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * 查询商家提现列表
     * @param withdrawNo
     * @param businessNo
     * @param startDate
     * @param endDate
     * @param start
     * @param end
     * @return
     */
    List<BusinessWithdraw> listBusinessWithdraw(@Param("withdrawNo") String withdrawNo, @Param("businessNo") String businessNo, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("active") boolean active, @Param("start") int start, @Param("end") int end);


    /**
     * 查询商家提现列表
     * @param withdrawNo
     * @param businessNo
     * @param startDate
     * @param endDate
     * @return
     */
    int countBusinessWithdraw(@Param("withdrawNo") String withdrawNo, @Param("businessNo") String businessNo, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("active") boolean active);

}
