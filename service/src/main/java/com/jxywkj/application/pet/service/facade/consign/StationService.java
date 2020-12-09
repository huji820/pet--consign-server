package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.Station;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


/**
 * @Author LiuXiangLin
 * @Description 站点
 * @Date 9:18 2019/8/27
 * @Param
 * @return
 **/
public interface StationService {

    /**
     * 获取托运站
     *
     * @param stationNo
     * @return
     */
    Station getStation(int stationNo);

    /**
     * 后台站点分页查询
     *
     * @param station
     * @param start
     * @param limit
     * @return
     */
    List<Station> listAdminStation(Station station, int start, int limit);

    /**
     * 后台站点分页查询
     *
     * @param station
     * @return
     */
    int countAdminStation(Station station);

    /**
     * 根据始发城市获取工作站
     *
     * @param startCity
     * @return
     */
    List<Station> listStation(String startCity);

    /**
     * 上传站点收款二维码
     * @param collectionQRCode
     * @param stationNo
     * @return
     */
    int uploadCollectionQRCode(String collectionQRCode,int stationNo);

    /**
     * 修改工作站信息
     *
     * @param station
     */
    int updateStation(Station station);


    /**
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Station>
     * @Author LiuXiangLin
     * @Description 获取所有的站点信息
     * @Date 9:35 2019/7/13
     * @Param []
     **/
    List<Station> listAllStation();


    /**
     * @return com.jxywkj.application.pet.model.consign.Station
     * @Author LiuXiangLin
     * @Description 通过电话号码查询站点
     * @Date 14:46 2019/9/17
     * @Param [phone]
     **/
    Station getByPhone(String phone);


    /**
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Station>
     * @Author LiuXiangLin
     * @Description 通过省市查询数据
     * @Date 8:42 2019/9/19
     * @Param [province, city]
     **/
    List<Station> listStationByProvinceAndCity(String province, String city);


    /**
     * @param staffOpenId 员工openId
     * @return com.jxywkj.application.pet.model.consign.Station
     * @author LiuXiangLin
     * @description 通过openId查询这个员工所属的站点（员工或者站点管理员 只取第一条）
     * @date 10:07 2019/10/12
     **/
    Station getStationByStaffOpenId(String staffOpenId);

    /**
     * @param stationNo
     * @return java.lang.String
     * @author LiuXiangLin
     * @description 通过站点编号查询电话号码
     * @date 11:01 2019/10/14
     **/
    String getPhoneByStationNo(int stationNo);

    /**
     * @param openId
     * @return com.jxywkj.application.pet.model.consign.Station
     * @author LiuXiangLin
     * @description 通过管理员openId查询数据
     * @date 14:01 2019/10/14
     **/
    Station getByAdminOpenId(String openId);

    /**
     * 通过员工unionId获取站点
     * @param unionId
     * @return
     */
    Station getByUnionId(String unionId);

    /**
     * 通过员工openId获取站点
     * @param openId
     * @return
     */
    Station getByOpenId(String openId);

    /**
     * @param orderNo
     * @return com.jxywkj.application.pet.model.consign.Station
     * @author LiuXiangLin
     * @description 通过订单编号查询站点
     * @date 16:05 2019/10/14
     **/
    Station getByOrderNo(String orderNo);

    /**
     * @param transportNo
     * @return com.jxywkj.application.pet.model.consign.Station
     * @author LiuXiangLin
     * @description 通过运输路线编号查询数据
     * @date 17:55 2019/10/16
     **/
    List<Station> getByTransportNo(int transportNo);

    /**
     * 删除站点
     *
     * @param stations
     * @return
     */
    int deleteStation(List<Station> stations);

    /**
     * <p>
     * 通过customerNo查询管理员站点（管理员）
     * </p>
     *
     * @param customerNo 用户编号
     * @return com.jxywkj.application.pet.model.consign.Station
     * @author LiuXiangLin
     * @date 15:43 2020/3/4
     **/
    Station getByCustomerNo(String customerNo);

    String addStationMedia(String stationNo, MultipartFile[] multipartFiles) throws IOException;

    /**
     * 根据经纬度查询站点列表
     * @param longitude  经度
     * @param latitude  纬度
     * @param offset
     * @param limit
     * @return
     */
    List<Station> listByPosition(double longitude, double latitude, int offset, int limit);

    /**
     * 查询通过城市分组的站点数据
     * @return
     */
    List<Station> listGroupByCity();

    /**
     * 通过省获取省下面的所有站点
     * @return
     */
    List<Station> listByProvince(String province);

    /**
     * 获取当前城市的所有站点，并可以通过距离、单量、服务排序
     * @param lng   经度
     * @param lat   维度
     * @param distanceSort  距离排序规则  1升序   2降序  其它不排序
     * @param orderNum      单量排序    1升序   2降序  其它不排序
     * @param serviceEval   服务评价排序  1升序   2降序  其它不排序
     * @param city      城市
     * @return
     */
    List<Station> listStationByCity(double lng, double lat, String distanceSort,
                                    String orderNum, String serviceEval, String city);

    /**
     * 判断站点是否属于当前城市  true 属于   false 不属于
     * @param city   城市名
     * @param stationNo  站点编号
     * @return
     */
    boolean stationBelongToCity(String city,String stationNo);

    /**
     * 查询可用站点的城市
     *
     * @param
     * @return java.util.List<java.lang.String>
     * @author HuZhengYu
     * @date 10:37 2020/9/30
     */
    List<String> listStartCity();
}
