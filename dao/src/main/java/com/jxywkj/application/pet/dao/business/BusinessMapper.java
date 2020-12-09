package com.jxywkj.application.pet.dao.business;

import com.jxywkj.application.pet.model.business.Business;
import com.jxywkj.application.pet.model.business.Business2;
import com.jxywkj.application.pet.model.common.BusinessPosition;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName BusinessMapper
 * @Description: 用户Mapper
 * @Author Aze
 * @Date 2019/7/13 11:09
 * @Version 1.0
 **/
@Component
public interface BusinessMapper {
    /**
     * <p>
     * 新增一条商家数据
     * </p>
     *
     * @param business 商家
     * @return int
     * @author LiuXiangLin
     * @date 14:26 2020/3/6
     **/
    int saveBusiness(@Param("business") Business business);

    /**
     * <p>
     * 登录
     * </p>
     *
     * @param business 商家对象
     * @return com.jxywkj.application.pet.model.business.Business
     * @author LiuXiangLin
     * @date 14:26 2020/3/6
     **/
    Business login(@Param("business") Business business);


    /**
     * <p>
     * 通过商家编号以及商家转态查询商家信息
     * </p>
     *
     * @param businessNo 商家编号
     * @param state      状态
     * @return com.jxywkj.application.pet.model.business.Business
     * @author LiuXiangLin
     * @date 14:28 2020/3/6
     **/
    Business getByBusinessNo(@Param("businessNo") String businessNo, @Param("state") int state);
    /**
     * <p>
     * 通过商家编号查询商家信息
     * </p>
     *
     * @param businessNo 商家编号
     * @return com.jxywkj.application.pet.model.business.Business
     * @author GuoPengCheng
     * @date 11:26 2020/7/21
     **/
    Business selByBusinessByNo(@Param("businessNo") String businessNo);

    /**
     * <p>
     * 通过位置查询商家列表
     * </p>
     *
     * @param businessPosition 商家对象
     * @param state            状态
     * @param offset           排除条数
     * @param limit            显示条数
     * @return java.util.List<com.jxywkj.application.pet.model.business.Business>
     * @author LiuXiangLin
     * @date 14:29 2020/3/6
     **/
    List<Business> listByPosition(@Param("businessPosition") BusinessPosition businessPosition,
                                  @Param("state") int state,
                                  @Param("offset") int offset,
                                  @Param("limit") int limit);


    /**
     * <p>
     * 通过跳条件模糊查询商家信息
     * </p>
     *
     * @param business  商家对象
     * @param startDate 注册开始日期
     * @param endDate   注册结束日期
     * @param start     开始条数
     * @param limit     显示条数
     * @param state     商家状态
     * @return java.util.List<com.jxywkj.application.pet.model.business.Business>
     * @author LiuXiangLin
     * @date 14:31 2020/3/6
     **/
    List<Business> listAdminBusiness(@Param("business") Business business,
                                     @Param("startDate") String startDate,
                                     @Param("endDate") String endDate,
                                     @Param("start") int start,
                                     @Param("limit") int limit,
                                     @Param("state") String state);

    /**
     * <p>
     * 通过条件查询商家总数
     * </p>
     *
     * @param business 商家对象
     * @param state    状态
     * @return int
     * @author LiuXiangLin
     * @date 14:33 2020/3/6
     **/
    int countAdminBusiness(@Param("business") Business business, @Param("state") String state);


    /**
     * <p>
     * 通过电话号码以及商家状态查询商家
     * </p>
     *
     * @param phone 电话号码
     * @param state 商家状态
     * @return com.jxywkj.application.pet.model.business.Business
     * @author LiuXiangLin
     * @date 14:55 2020/3/6
     **/
    Business getBusinessByPhoneAndState(@Param("phone") String phone, @Param("state") int state);


    /**
     * <p>
     * 通过省市以及商家状态查询商家列表
     * </p>
     *
     * @param province 省
     * @param city     市
     * @param state    状态
     * @return java.util.List<com.jxywkj.application.pet.model.business.Business>
     * @author LiuXiangLin
     * @date 14:56 2020/3/6
     **/
    List<Business> listByProvinceCityAndType(@Param("province") String province,
                                             @Param("city") String city,
                                             @Param("state") int state);

    /**
     * <p>
     * 通过省和状态查询商家列表
     * </p>
     *
     * @param province 省
     * @param state    状态
     * @return java.util.List<com.jxywkj.application.pet.model.business.Business>
     * @author LiuXiangLin
     * @date 11:58 2019/12/10
     **/
    List<Business> listByProvinceAndType(@Param("province") String province,
                                         @Param("state") int state);


    /**
     * <p>
     * 更新商家状态
     * </p>
     *
     * @param business 商家对象
     * @return int
     * @author LiuXiangLin
     * @date 14:57 2020/3/6
     **/
    int updateBusinessState(@Param("business") Business business);


    /**
     * <p>
     * 通过电话号码以及状态查询商家状态
     * </p>
     *
     * @param phone 电话号码
     * @param state 状态
     * @return com.jxywkj.application.pet.model.business.Business
     * @author LiuXiangLin
     * @date 14:58 2020/3/6
     **/
    Business getByPhone(@Param("phone") String phone, @Param("state") String state);


    /**
     * <p>
     * 通过商家编号删除数据
     * </p>
     *
     * @param businessNo 商家编号
     * @return int
     * @author LiuXiangLin
     * @date 14:35 2020/1/10
     **/
    int deleteByBusinessNo(@Param("businessNo") String businessNo);

    /**
     * <p>
     * 通过openId查询商家信息
     * </p>
     *
     * @param openId openid
     * @return com.jxywkj.application.pet.model.business.Business
     * @author LiuXiangLin
     * @date 15:14 2020/4/7
     **/
    Business getByOpenId(@Param("openId") String openId);

    /**
     * 通过unionId查找商家信息
     * @param unionId
     * @return
     */
    Business getByUnionId(@Param("unionId")String unionId);

    /**
     * <p>
     * 查询通过城市分组的数据
     * </p>
     *
     * @return java.util.List<com.jxywkj.application.pet.model.business.Business>
     * @author LiuXiangLin
     * @date 14:17 2020/4/23
     **/
    List<Business> listByGroupCity();

    /**
     * <p>
     * 通过省查询该省下的所有商家
     * </p>
     *
     * @param province 省
     * @return java.util.List<com.jxywkj.application.pet.model.business.Business>
     * @author LiuXiangLin
     * @date 14:25 2020/4/23
     **/
    List<Business> listByProvince(String province);

    /**
     * 通过customerNo查看商家是否领取优惠卷
     * @param customerNo
     * @return
     */
    Integer getHaveNewGiftBagByCustomerNo(@Param("customerNo")String customerNo);

    /**
     * 第一次登录新增商家
     *
     * @param business2
     * @return void
     * @author HuZhengYu
     * @date 14:10 2020/9/28
     */
    void save(
            @Param("business") Business2 business2);
}


