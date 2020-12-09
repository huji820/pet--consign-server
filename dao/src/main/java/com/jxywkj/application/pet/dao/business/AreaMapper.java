package com.jxywkj.application.pet.dao.business;

import com.jxywkj.application.pet.model.vo.area.AreaCityVO;
import com.jxywkj.application.pet.model.vo.area.AreaDistrictVO;
import com.jxywkj.application.pet.model.vo.area.AreaProvinceVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AreaMapper {

    /**
     *
     * 获取省份列表
     * @param
     * @return java.util.List<com.jxywkj.application.pet.model.vo.area.AreaProvinceVO>
     * @author yang hituzi
     * @date 11:37 2020/4/23
     */
    List<AreaProvinceVO> listProvince();

    /**
     *
     * 获取该省的所有地级市
     * @param provinceId
     * @return java.util.List<com.jxywkj.application.pet.model.vo.area.AreaCityVO>
     * @author yang hituzi
     * @date 13:49 2020/4/23
     */
    List<AreaCityVO> listCity(@Param("provinceId") Integer provinceId);

    /**
     *
     * 通过省份名称获取身份主键
     * @param provinceName
     * @return java.lang.Integer
     * @author yang hituzi
     * @date 14:28 2020/4/23
     */
    Integer getProvinceId(@Param("provinceName") String provinceName);

    List<AreaDistrictVO> listDistrict(@Param("cityId") Integer cityId);

    Integer getCityId(@Param("cityName") String cityName);

    /**
     * 查询全部市名
     *
     * @return java.util.List<java.lang.String>
     * @author HuZhengYu
     * @date 10:55 2020/9/30
     */
    List<String> listAllCity();

    /**
     * 查询全部市名
     *
     * @param
     * @return java.util.List<java.lang.String>
     * @author HuZhengYu
     * @date 10:54 2020/9/30
     */
    List<String> listAllCityByName(String name);
}
