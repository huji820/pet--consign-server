package com.jxywkj.application.pet.admin.station;

import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.service.facade.consign.StationService;
import com.jxywkj.application.pet.service.facade.consign.TransportService;
import com.yangwang.sysframework.utils.JsonUtil;
import com.yangwang.sysframework.utils.Pagination;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Administrator
 * @Date 2019-12-08 14:50
 * @Version 1.0
 */
@RestController("adminCityController")
@RequestMapping("admin/station/city")
public class CityController {

    @Autowired
    TransportService transportService;

    @GetMapping("")
    public List<City> listCity() {
        return newCityResult(transportService.listStartCity());
    }


    public static class City {
        String cityName;

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public City() {
        }

        public City(String cityName) {
            this.cityName = cityName;
        }
    }

    /**
     * <p>
     * 获取cityReult对象
     * </p>
     *
     * @param transports 运输路线
     * @return com.jxywkj.application.pet.api.consign.city.CityResult
     * @author LiuXiangLin
     * @date 17:33 2020/1/4
     **/
    private List<City> newCityResult(List<String> transports) {
        List<City> result = new ArrayList<>();
        City city = null;
        for (String transport : transports) {
            city = new City(transport);
            result.add(city);
            city = null;
        }
        return result;
    }
}
