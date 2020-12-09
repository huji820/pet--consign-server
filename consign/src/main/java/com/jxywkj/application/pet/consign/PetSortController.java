package com.jxywkj.application.pet.consign;

import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.service.facade.common.PetSortService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName PetTypeController
 * @Description 宠物类型
 * @Author LiuXiangLin
 * @Date 2019/8/15 11:44
 * @Version 1.0
 **/
@RestController
@RequestMapping("/consign/petType")
public class PetSortController {
    @Resource
    PetSortService petSortService;

    @GetMapping("/listAll")
    public JsonResult listAll() {
        return JsonResult.success(petSortService.listAllType());
    }
}
