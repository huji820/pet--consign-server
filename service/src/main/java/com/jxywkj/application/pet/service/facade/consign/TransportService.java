package com.jxywkj.application.pet.service.facade.consign;


import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.model.consign.Transport;
import org.apache.ibatis.annotations.Param;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface TransportService {

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
    List<Transport> listTransport(String endCity, int transportType, int stationNo, int start, int end);


    /**
     * 根据开始城市结束城市获取运输站点信息
     *
     * @param startCity
     * @param endCity
     * @param transportType
     * @return
     */
    Transport getTransport(int cityNo, String startCity, String endCity, int transportType);

    /**
     * 获取网点数量
     *
     * @param endCity
     * @param transportType
     * @return
     */
    int countTransport(String endCity, int transportType, int stationNo);

    /**
     * 修改价格
     *
     * @param transport
     */
    void updateTransport(Transport transport);

    /**
     * 新增运输线路
     *
     * @param transport
     */
    void insertTransport(Transport transport,Staff staff);

    /**
     * 手动添加一条路线
     *
     * @param transport
     * @param staff
     * @param province
     * @throws IOException
     */
    void insertAddress(Transport transport, Staff staff, String province) throws Exception;

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
     * @Description 查询目的城市
     * @Date 10:47 2019/7/13
     * @Param [startCity, transportNo]
     **/
    List<String> listEndCity(String startCity, int transportNo);

    /**
     * @return java.util.List<java.lang.String>
     * @Author LiuXiangLin
     * @Description 查询可用的运输方式
     * @Date 10:47 2019/7/13
     * @Param [startCity, endCity]
     **/
    List<Integer> listTransportType(String startCity, String endCity);

    /**
     * @return com.jxywkj.application.pet.model.consign.Transport
     * @Author LiuXiangLin
     * @Description 通过条件查询运输
     * @Date 14:41 2019/7/13
     * @Param [transport]
     **/
    List<Transport> listTransportByCondition(Transport transport);

    /**
     * @return
     * @Author Aze
     * @Description: 通过transportNo查询网挖网点路线
     * @Date:
     * @Param
     */

    Transport getTransportByTransportNo(int transportNo);

    /**
     * @return
     * @Author Aze
     * @Description: 查询所有开始城市
     * @Date:
     * @Param
     */
    List<String> listStartCity();

    /**
     * 通过运输方式主键获取起始城市
     * @return
     */
    String getStartCityByTransportNo(int transportId);

    /**
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Transport>
     * @Author LiuXiangLin
     * @Description 通过其实城市和终点城市获取运输列表
     * @Date 16:42 2019/9/11
     * @Param [startCity, endCity, transportType]
     **/
    List<Transport> listByCityAndType(String startCity, String endCity, int transportType);


    /**
     * @return java.util.List<java.lang.Integer>
     * @Author LiuXiangLin
     * @Description 通过站点编号获取所有的运输路线编号
     * @Date 18:33 2019/9/18
     * @Param [stationNo]
     **/
    List<Integer> listAllTransportNoByStationNo(int stationNo);

    /**
     * @return java.util.List<java.lang.Integer>
     * @Author LiuXiangLin
     * @Description 通过站点编号和运输方式查询运输编号
     * @Date 17:24 2019/9/19
     * @Param [stationNo, transportType]
     **/
    List<Integer> listByStationNoAndTransportType(int stationNo, int... transportType);

    /**
     * 更新运输路线的最大运输重量
     *
     * @param transport 运输路线对象
     * @return int
     * @author LiuXiangLin
     * @date 10:33 2019/10/21
     **/
    int updateTransportPrice(Transport transport);

    /**
     * <p>
     * 更新线路最起步运输重量
     * </p>
     *
     * @param transport 运输录像对象
     * @return int
     * @author LiuXiangLin
     * @date 9:30 2019/11/22
     **/
    int updateTransportStartWeight(Transport transport);

    /**
     * <p>
     * 查询一定距离内的
     * </p>
     *
     * @param instance 距离
     * @param station  站点
     * @return java.util.List<com.jxywkj.application.pet.model.consign.Transport>
     * @author LiuXiangLin
     * @date 17:39 2020/4/16
     **/
    List<Transport> listByInstance(Station station, BigDecimal instance);

    /**
     * 如果运输路线不存在，通过订单编号设置默认运输路线
     * @param orderNo
     * @return
     */
    Transport getTransportByOrderNo(String orderNo);
}
