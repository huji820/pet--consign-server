package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.common.StationPosition;
import com.jxywkj.application.pet.model.consign.Station;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 工作站的Mapper
 *
 * @Description
 * @Author Administrator
 * @Date 2019-06-20 21:11
 * @Version 1.0
 */
@Mapper
public interface StationMapper {

    /**
     * 获取托运站
     *
     * @param stationNo
     * @return
     */
    Station getStation(int stationNo);


    /**
     * 根据始发城市获取工作站
     *
     * @param cityName
     * @return
     */
    List<Station> listStationByCityName(@Param("cityName") String cityName);

    /**
     * 上传站点收款二维码
     * @param collectionQRCode
     * @param stationNo
     * @return
     */
    int uploadCollectionQRCode(@Param("collectionQRCode")String collectionQRCode,
                               @Param("stationNo")int stationNo);

    /**
     * 修改工作站信息
     *
     * @param station
     */
    int updateStation(Station station);

    /**
     * @param
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Station>
     * @author LiuXiangLin
     * @description 查询所有的站点信息
     * @date 9:38 2019/7/13
     **/
    List<Station> listAllStation();

    /**
     * 后台站点分页查询
     * @param station
     * @param start
     * @param limit
     * @return
     */
    List<Station> listAdminStation(@Param("station") Station station, @Param("start") int start, @Param("limit") int limit);

    /**
     * 后台站点分页查询
     * @param station
     * @return
     */
    int countAdminStation(@Param("station") Station station);

    /**
     * @return com.jxywkj.application.pet.model.consign.Station
     * @Author LiuXiangLin
     * @Description 通过站点联系人查询站点
     * @Date 14:48 2019/9/17
     * @Param [phone]
     **/
    Station getByPhone(@Param("phone") String phone);

    /**
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Station>
     * @Author LiuXiangLin
     * @Description 通过省市查询站点信息
     * @Date 8:43 2019/9/19
     * @Param [province, city]
     **/
    List<Station> listByProvinceAndCity(@Param("province") String province, @Param("city") String city);

    /**
     * @param [openId , staffState]
     * @return com.jxywkj.application.pet.model.consign.Station
     * @author LiuXiangLin
     * @description 通过opend获取这个员工所属的站点（只取第一条）
     * @date 10:10 2019/10/12
     **/
    Station getByStaffOpenId(@Param("openId") String openId, @Param("staffState") String staffState );

    /**
     * @author LiuXiangLin
     *  通过站点编号查询电话号码
     * @date 11:02 2019/10/14
     * @param stationNo 站点编号
     * @return java.lang.String 电话号码
     **/
    String getPhoneByStationNo(@Param("stationNo") int stationNo);
    
    /**
     * @author LiuXiangLin
     * @description 通过管理员openId查询数据
     * @date 13:57 2019/10/14
     * @param [adminOpenId]
     * @return com.jxywkj.application.pet.model.consign.Station
     **/
    Station getByAdminOpenId(@Param("adminOpenId") String adminOpenId,@Param("staffState") String staffState);

    /**
     * 通过员工unionId获取站点
     * @param unionId
     * @param staffState
     * @return
     */
    Station getByUnionId(@Param("unionId") String unionId,@Param("staffState") String staffState);

    /**
     * 通过员工uopenId获取站点
     * @param openId
     * @param staffState
     * @return
     */
    Station getByOpenId(@Param("openId") String openId,@Param("staffState") String staffState);

    /**
     * 通过管理员unionId查询数据
     * @param adminUnionId
     * @param staffState
     * @return
     */
    Station getByAdminUnionId(@Param("adminUnionId")String adminUnionId,@Param("staffState") String staffState);

    /**
     * @author LiuXiangLin
     * @description 通过订单编号订单所属站点
     * @date 16:06 2019/10/14
     * @param [orderNo]
     * @return com.jxywkj.application.pet.model.consign.Station
     **/
    Station getByOrderNo(String orderNo);

    /**
     * @author LiuXiangLin
     * @description 通过运输路线查询站点信息
     * @date 17:56 2019/10/16
     * @param [transportNo]
     * @return com.jxywkj.application.pet.model.consign.Station
     **/
    List<Station> getByTransportNo(@Param("transportNo") int transportNo);


    /**
     * 删除站点
     * @param stations
     * @return
     */
    int deleteStation(@Param("stations") List<Station> stations);

    /**
     * <p>
     * 通过用户编号查询站点信息，
     * </p>
     *
     * @param customerNo
     * @return com.jxywkj.application.pet.model.consign.Station
     * @author LiuXiangLin
     * @date 14:01 2020/3/4
     **/
    Station getByCustomerNo(@Param("customerNo")String customerNo);

    /**
     * <p>
     * 通过位置查询站点列表
     * </p>
     * @param stationPosition 商家对象
     * @param state            状态
     * @param offset           排除条数
     * @param limit            显示条数
     **/
    List<Station> listByPosition(@Param("stationPosition") StationPosition stationPosition,
                                  @Param("state") int state,
                                  @Param("offset") int offset,
                                  @Param("limit") int limit);

    /**
     * 查询通过城市分组的站点数据
     * @return
     */
    List<Station> listGroupByCity();

    /**
     * 通过省获取省下面的所有站点
     * @return
     */
    List<Station> listByProvince(@Param("province")String province);

    /**
     * 获取当前城市的所有站点，并可以通过距离、单量、服务排序
     * @param lng   经度
     * @param lat   维度
     * @param distanceSort  距离排序规则
     * @param orderNum      单量排序
     * @param serviceEval   服务评价排序
     * @param city      城市
     * @return
     */
    List<Station> listStationByCity(@Param("lng")double lng,
                                    @Param("lat")double lat,
                                    @Param("distanceSort")String distanceSort,
                                    @Param("orderNum")String orderNum,
                                    @Param("serviceEval")String serviceEval,
                                    @Param("city")String city);

    /**
     * 查询可用站点的城市
     *
     * @param
     * @return java.util.List<java.lang.String>
     * @author HuZhengYu
     * @date 10:38 2020/9/30
     */
    List<String> listStartCity();
}
