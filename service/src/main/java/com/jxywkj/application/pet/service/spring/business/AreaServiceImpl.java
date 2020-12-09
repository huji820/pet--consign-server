package com.jxywkj.application.pet.service.spring.business;

import com.jxywkj.application.pet.dao.business.AreaMapper;
import com.jxywkj.application.pet.model.vo.area.AreaCityVO;
import com.jxywkj.application.pet.model.vo.area.AreaDistrictVO;
import com.jxywkj.application.pet.model.vo.area.AreaProvinceVO;
import com.jxywkj.application.pet.service.facade.business.AreaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yang hituzi
 * @description 
 * @date 
 */
@Service
public class AreaServiceImpl implements AreaService {

    @Resource
    AreaMapper areaMapper;

    @Override
    public List<AreaProvinceVO> listProvince() {
        return areaMapper.listProvince();
    }

    @Override
    public List<AreaCityVO> listCity(String provinceName) {
        Integer provinceId = areaMapper.getProvinceId(provinceName);
        if(provinceId == null){
            return null;
        }
        return areaMapper.listCity(provinceId);
    }

    @Override
    public List<AreaDistrictVO> listDistrict(String cityName) {
        Integer cityId = areaMapper.getCityId(cityName);
        if(cityId == null){
            return null;
        }
        return areaMapper.listDistrict(cityId);
    }

    @Override
    public Integer getCityId(String cityName) {
        return areaMapper.getCityId(cityName);
    }

    @Override
    public List<String> listAllCity() {
        return areaMapper.listAllCity();
    }

    @Override
    public List<String> listAllCityByName(String name) {
        return areaMapper.listAllCityByName(name);
    }
}
