package com.jxywkj.application.pet.service.facade.business;

import com.jxywkj.application.pet.model.vo.area.AreaCityVO;
import com.jxywkj.application.pet.model.vo.area.AreaDistrictVO;
import com.jxywkj.application.pet.model.vo.area.AreaProvinceVO;

import java.util.List;

/**
 * @author yang hituzi
 * @description 
 * @date 
 */
public interface AreaService {

    List<AreaProvinceVO> listProvince();

    List<AreaCityVO> listCity(String provinceName);

    List<AreaDistrictVO> listDistrict(String cityName);

    Integer getCityId(String cityName);

    /**
     * 查询全部市名
     *
     * @param
     * @return java.util.List<java.lang.String>
     * @author HuZhengYu
     * @date 10:54 2020/9/30
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
