package com.jxywkj.application.pet.api.common;

import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.service.facade.common.PetSortService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author LiuXiangLin
 * @Description 宠物类别
 * @Date 14:13 2019/8/21
 * @Param 
 * @return 
 **/

@Api(description = "宠物类别")
@RestController
@RequestMapping("/api/petType")
public class PetSortApiController {

    @Autowired
    PetSortService petSortService;

    @ApiOperation(value = "宠物类别列表")
    @GetMapping
    public JsonResult listPetType() {
        return JsonResult.success(petSortService.listPetTypeName());
    }
}
