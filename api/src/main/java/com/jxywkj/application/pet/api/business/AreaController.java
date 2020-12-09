package com.jxywkj.application.pet.api.business;

import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.service.facade.business.AreaService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author yang hituzi
 * @description 
 * @date 
 */
@RestController
@RequestMapping("/api/area")
public class AreaController {

    @Resource
    AreaService areaService;

    @PostMapping("/province")
    public JsonResult listProvince(){
        return JsonResult.success(areaService.listProvince());
    }

    @GetMapping("/city")
    public JsonResult listCity(@RequestParam("name") String name){
        return JsonResult.success(areaService.listCity(name));
    }

    /**
     * 查询全部市名
     *
     * @return com.jxywkj.application.pet.common.utils.JsonResult
     * @author HuZhengYu
     * @date 10:53 2020/9/30
     */
    @GetMapping("/listAllCity")
    public JsonResult listAllCity() {
        return JsonResult.success(areaService.listAllCity());
    }


    /**
     * 查询全部市名
     *
     * @return com.jxywkj.application.pet.common.utils.JsonResult
     * @author HuZhengYu
     * @date 10:53 2020/9/30
     */
    @GetMapping("/listAllCityByName")
    public JsonResult listAllCity(@RequestParam(required = false, defaultValue = "") String name) {
        return JsonResult.success(areaService.listAllCityByName(name));
    }

    @GetMapping("/district")
    public JsonResult listDistrict(@RequestParam("name") String name){
        return JsonResult.success(areaService.listDistrict(name));
    }
}
