package com.jxywkj.application.pet.consign;

import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.service.facade.common.PetGenreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName PetClassController
 * @Description 宠物类型
 * @Author LiuXiangLin
 * @Date 2019/8/14 18:28
 * @Version 1.0
 **/
@RestController
@RequestMapping("/consign/petClassify")
public class PetClassController {
    @Resource
    PetGenreService petGenreService;

    @GetMapping("/listAll")
    public JsonResult listTypeWidthClassify() {
        return JsonResult.success(petGenreService.listAll());
    }

    @GetMapping("/listByTypeName")
    public JsonResult listByTypeName(@RequestParam("typeName") String typeName) {
        return JsonResult.success(petGenreService.listPetClassifyByTypeName(typeName));
    }

}
