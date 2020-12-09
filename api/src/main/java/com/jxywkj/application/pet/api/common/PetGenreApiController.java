package com.jxywkj.application.pet.api.common;

import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.service.facade.common.PetGenreService;
import com.jxywkj.application.pet.service.facade.common.PetSortService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author LiuXiangLin
 * @Description 宠物
 * @Date 14:12 2019/8/21
 * @Param
 * @return
 **/
@Api(description = "宠物")
@RestController
@RequestMapping("/api/petClassify")
public class PetGenreApiController {
    @Autowired
    PetGenreService petGenreService;

    @Autowired
    PetSortService petSortService;

    @ApiImplicitParam(name = "petTypeName", required = true, type = "String", value = "宠物类别名称（猫，狗）")
    @ApiOperation(value = "根据宠物类别获取宠物品种列表")
    @GetMapping("")
    public JsonResult listPetClassifyByTypeName(@RequestParam("petTypeName") String petTypeName) {
        return JsonResult.success(petGenreService.listPetClassifyByTypeName(petTypeName));
    }

    @ApiOperation(value = "获取所有的宠物品种（如金毛，加菲猫）")
    @GetMapping("/listAll")
    public JsonResult listAll() {
        return JsonResult.success(petGenreService.listAll());
    }

    @GetMapping("/keyWord/{keyWord:\\S+}")
    @ApiOperation(value = "模糊查询数据")
    public JsonResult listByKeyWord(@PathVariable("keyWord") String keyWord) {
        return JsonResult.success(petGenreService.listByKeyWord(keyWord));
    }
}
