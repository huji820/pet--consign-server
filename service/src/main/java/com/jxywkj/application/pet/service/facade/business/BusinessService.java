package com.jxywkj.application.pet.service.facade.business;

import com.jxywkj.application.pet.common.enums.BusinessStateEnum;
import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.business.Business;
import com.jxywkj.application.pet.model.business.Business2;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.model.vo.AuthVo;

import java.util.List;

/**
 * @ClassName BusinessService
 * @Description: 商家端
 * @Author Aze
 * @Date 2019/8/14 13:43
 * @Version 1.0
 **/
public interface BusinessService {
    /**
     * <p>
     * 商家登录
     * </p>
     *
     * @param business 商家对象
     * @return com.jxywkj.application.pet.model.business.Business
     * @author LiuXiangLin
     * @date 14:13 2020/3/6
     **/
    Business login(Business business);

    /**
     * <p>
     * 添加一个商家
     * </p>
     *
     * @param business 商家对象
     * @return int
     * @author LiuXiangLin
     * @date 14:13 2020/3/6
     **/
    int saveBusiness(Business business);


    /**
     * <p>
     * 通过商家编号查询状态正常的商家
     * </p>
     *
     * @param businessNo        商家编号
     * @param businessStateEnum 商家状态
     * @return com.jxywkj.application.pet.model.business.Business
     * @author LiuXiangLin
     * @date 14:13 2020/3/6
     **/
    Business getByBusinessNo(String businessNo, BusinessStateEnum businessStateEnum);

    /**
     * <p>
     * 通过商家编号查询商家详情
     * </p>
     *
     * @param businessNo        商家编号
     * @return com.jxywkj.application.pet.model.business.Business
     * @author GuoPengCheng
     * @date 11:34 2020/7/21
     **/
    Business selByBusinessByNo(String businessNo);

    /**
     * <p>
     * 通过经纬度查询商家列表
     * </p>
     *
     * @param longitude         经度
     * @param latitude          纬度
     * @param offset            排除条数
     * @param limit             显示条数
     * @param businessStateEnum 商家状态
     * @return java.util.List<com.jxywkj.application.pet.model.business.Business>
     * @author LiuXiangLin
     * @date 14:15 2020/3/6
     **/
    List<Business> listByPosition(double longitude, double latitude, int offset, int limit, BusinessStateEnum businessStateEnum);


    /**
     * <p>
     * 根据条件查询商家列表
     * </p>
     *
     * @param business          商家对象
     * @param start             开始条数
     * @param limit             显示条数
     * @param businessStateEnum 商家状态
     * @param startDate         注册开始日期
     * @param endDate           注册结束日期
     * @return java.util.List<com.jxywkj.application.pet.model.business.Business>
     * @author LiuXiangLin
     * @date 14:17 2020/3/6
     **/
    List<Business> listAdminBusiness(Business business,String startDate, String endDate, int start, int limit, BusinessStateEnum businessStateEnum);


    /**
     * <p>
     * 根据条件商家总数
     * </p>
     *
     * @param business          商家对象
     * @param businessStateEnum 商家类型
     * @return int
     * @author LiuXiangLin
     * @date 14:18 2020/3/6
     **/
    int countAdminBusiness(Business business, BusinessStateEnum businessStateEnum);

    /**
     * <p>
     * 商家注册
     * </p>
     *
     * @param business 商家对象
     * @return int
     * @author LiuXiangLin
     * @date 14:19 2020/3/6
     **/
    int registerBusiness(Business business);

    /**
     * <p>
     * 通过电话号码查询商家
     * </p>
     *
     * @param phone             商家电话号码
     * @param businessStateEnum 商家状态
     * @return com.jxywkj.application.pet.model.business.Business
     * @author LiuXiangLin
     * @date 14:20 2020/3/6
     **/
    Business getBusinessByPhone(String phone, BusinessStateEnum businessStateEnum);


    /**
     * <p>
     * 获取未审核的商家
     * </p>
     *
     * @param station 站点对象（包含省市信息）
     * @return java.util.List<com.jxywkj.application.pet.model.business.Business>
     * @author LiuXiangLin
     * @date 14:21 2020/3/6
     **/
    List<Business> listUnauditedBusiness(Station station);

    /**
     * <p>
     * 更新商家状态
     * </p>
     *
     * @param business 商家对象
     * @return int
     * @author LiuXiangLin
     * @date 14:22 2020/3/6
     **/
    int updateBusinessState(Business business);

    /**
     * <p>
     * 驳回商家
     * </p>
     *
     * @param business 被驳回的商家对象
     * @return int
     * @author LiuXiangLin
     * @date 14:22 2020/3/6
     **/
    int rejectBusiness(Business business);

    /**
     * <p>
     * 通过openId查询商家信息
     * </p>
     *
     * @param openId openid
     * @return com.jxywkj.application.pet.model.business.Business
     * @author LiuXiangLin
     * @date 15:13 2020/4/7
     **/
    Business getByOpenId(String openId);

    /**
     * 通过unionId查找商家信息
     * @param unionId
     * @return
     */
    Business getByUnionId(String unionId);
    /**
     * <p>
     * 按城市groupby查询商家列表
     * </p>
     *
     * @return java.util.List<com.jxywkj.application.pet.model.business.Business>
     * @author LiuXiangLin
     * @date 14:15 2020/4/23
     **/
    List<Business> listByGroupCity();

    /**
     * <p>
     * 通过省获取该省下所有的商家
     * </p>
     *
     * @param province 省
     * @return java.util.List<com.jxywkj.application.pet.model.business.Business>
     * @author LiuXiangLin
     * @date 14:24 2020/4/23
     **/
    List<Business> listByProvince(String province);

    /**
     * 通过customerNo查看商家是否领取优惠卷
     * @param customerNo
     * @return
     */
    Integer getHaveNewGiftBagByCustomerNo(String customerNo);

    /**
     * 更新商家信息
     *
     * @param business2
     * @return java.lang.Object
     * @author HuZhengYu
     * @date 18:03 2020/9/27
     */
    JsonResult update(Business2 business2);

    /**
     * 通过商家编号查询认证
     *
     * @param businessNo
     * @return com.jxywkj.application.pet.common.utils.JsonResult
     * @author HuZhengYu
     * @date 10:38 2020/9/28
     */
    JsonResult getAuthByBusinessNo(String businessNo);

    /**
     * 商家认证
     *
     * @param authVo
     * @return com.jxywkj.application.pet.common.utils.JsonResult
     * @author HuZhengYu
     * @date 10:39 2020/9/28
     */
    JsonResult insetBusiness(AuthVo authVo);

    /**
     * 第一次登录新增商家
     *
     * @param business2
     * @return void
     * @author HuZhengYu
     * @date 14:10 2020/9/28
     */
    void save(Business2 business2);
}
