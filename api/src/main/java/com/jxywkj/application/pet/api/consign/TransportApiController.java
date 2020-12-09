package com.jxywkj.application.pet.api.consign;

import com.jxywkj.application.pet.api.consign.city.CityBody;
import com.jxywkj.application.pet.api.consign.city.CityResult;
import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.service.facade.consign.TransportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName TransportApiController
 * @Description 运输
 * @Author LiuXiangLin
 * @Date 2019/7/13 11:09
 * @Version 1.0
 **/
@Api(description = "运输")
@RestController("apiTransportController")
@RequestMapping("/api/transport")
public class TransportApiController {
    @Resource
    TransportService transportService;

    @GetMapping("/listStartCity")
    @ApiOperation(value = "获取所有的的起始城市")
    public JsonResult listStartCity(@RequestParam(required = false, defaultValue = "0") int transportType) {
        return JsonResult.success(newCityResult(transportService.listStartCity()));
    }


    @GetMapping("/listEndCity")
    @ApiOperation(value = "获取所有的的目标城市")
    public JsonResult listEndCity(@RequestParam String startCity, @RequestParam(required = false, defaultValue = "0") int transportType) {
        return JsonResult.success(newCityResult(transportService.listEndCity(startCity, transportType)));
    }

    @GetMapping("/listTransportType")
    @ApiOperation(value = "获取开始城市，结束城市的运输方式")
    public JsonResult listTransportType(@RequestParam String startCity, @RequestParam(required = false, defaultValue = "") String endCity) {
        List<Integer> list = transportService.listTransportType(startCity, endCity);
        return JsonResult.success(list);
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
    private CityResult newCityResult(List<String> transports) {
        CityResult cityResult = new CityResult();
        CityBody cityBody = null;
        for (String transport : transports) {
            cityBody = new CityBody(transport);
            cityResult.addCityBody(cityBody);
            cityBody = null;
        }
        cityResult.sort();
        return cityResult;
    }
}
