package com.jxywkj.application.pet.dao.consign;


import com.jxywkj.application.pet.model.consign.Transport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface TransportMapper {
    /**
     * 获取网点列表
     *
     * @param endCity
     * @param transportType
     * @param stationNo
     * @param start
     * @param end
     * @return
     */
    List<Transport> listTransport(@Param("endCity") String endCity, @Param("transportType") int transportType, @Param("stationNo") int stationNo, @Param("start") int start, @Param("end") int end);

    /**
     * 根据开始城市结束城市获取运输站点信息
     *
     * @param cityNo
     * @param startCity
     * @param endCity
     * @param transportType
     * @return
     */
    Transport getTransport(@Param("cityNo") int cityNo, @Param("startCity") String startCity, @Param("endCity") String endCity, @Param("transportType") int transportType);

    /**
     * 获取网点数量
     *
     * @param endCity
     * @param transportType
     * @return
     */
    int countTransport(@Param("endCity") String endCity, @Param("transportType") int transportType, @Param("stationNo") int stationNo);


    /**
     * 修改线路价格
     *
     * @param transport
     */
    void updateTransport(Transport transport);

    /**
     * 新增运输线路
     *
     * @param transport
     */
    void insertTransport(Transport transport);


    /**
     * 删除合作伙伴下的所有运输线路
     *
     * @param partnerNo
     */
    void deleteTransportByPartnerNo(int partnerNo);

    /**
     * 删除运输线路
     *
     * @param transportNo
     */
    void deleteTransport(int transportNo);

    /**
     * @return java.util.List<java.lang.String>
     * @Author LiuXiangLin
     * @Description 通过运输方式查询所有的开始位置
     * @Date 10:12 2019/7/13
     * @Param [transportType]
     **/
    List<String> listStartCity();


    /**
     * @return java.util.List<java.lang.String>
     * @Author LiuXiangLin
     * @Description 通过开始城市查询目的城市
     * @Date 10:26 2019/7/13
     * @Param [startCity]
     **/
    List<String> listEndCity(@Param("startCity") String startCity, @Param("transportType") int transportType);

    /**
     * @return java.util.List<java.lang.String>
     * @Author LiuXiangLin
     * @Description 查询可用的运输方式
     * @Date 10:47 2019/7/13
     * @Param [startCity, endCity]
     **/
    List<Integer> listTransportType(@Param("startCity") String startCity, @Param("endCity") String endCity);

    /**
     * @return com.jxywkj.application.pet.model.consign.Transport
     * @Author LiuXiangLin
     * @Description 查询线路
     * @Date 14:57 2019/7/13
     * @Param [transport]
     **/
    List<Transport> listTransportByCondition(@Param("transport") Transport transport);

    /**
     * @return com.jxywkj.application.pet.model.consign.Transport
     * @Author LiuXiangLin
     * @Description 通过编号查询
     * @Date 15:13 2019/7/16
     * @Param [transportNo]
     **/
    Transport getTransportByTransportNo(@Param("transportNo") int transportNo);


    /**
     * @return java.util.List<java.lang.Integer>
     * @Author LiuXiangLin
     * @Description 通过城市获取所有的运输路线编号
     * @Date 18:30 2019/9/18
     * @Param [stationNo]
     **/
    List<Integer> listAllTransportNoByStationNo(@Param("cityNo") int cityNo);

    /**
     * @return java.util.List<java.lang.Integer>
     * @Author LiuXiangLin
     * @Description 通过城市编号以及运输方式查询运输主键
     * @Date 17:21 2019/9/19
     * @Param [cityNo, transportType]
     **/
    List<Integer> listByStationNoAndTransportType(@Param("cityNo") int cityNo, @Param("transportType") int[] transportType);

    /**
     * 更新站点的最大载重量
     *
     * @param transportNo 运输路线编号
     * @param maxWeight   更新的最大载重量
     * @return int
     * @author LiuXiangLin
     * @date 10:36 2019/10/21
     **/
    int updateTransportMaxWeight(@Param("transportNo") int transportNo, @Param("maxWeight") BigDecimal maxWeight);

    /**
     * <p>
     * 更新路线的起步重量
     * </p>
     *
     * @param transportNo 运输录像
     * @param startWeight 起步重量
     * @return int
     * @author LiuXiangLin
     * @date 9:32 2019/11/22
     **/
    int updateStartWeight(@Param("transportNo") int transportNo, @Param("startWeight") BigDecimal startWeight);

    /**
     * 通过运输路线主键获取起始城市
     * @param transportNo
     * @return
     */
    String getStartCityByTransportNo(@Param("transportNo") int transportNo);
    /**
     * <p>
     * 查询站点一定范围内的所有路线
     * </p>
     *
     * @param lnt      纬度
     * @param lat      经度
     * @param instance 距离
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Transport>
     * @author LiuXiangLin
     * @date 17:41 2020/4/16
     **/
    List<Transport> listByInstance(@Param("lnt") double lnt, @Param("lat") double lat, @Param("instance") BigDecimal instance);
}
