package com.jxywkj.application.pet.consign;

import com.jxywkj.application.pet.common.enums.Code;
import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.consign.BeyondPetNum;
import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.service.facade.consign.BeyondPetNumService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 加价配置
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className BeyondPetNumController
 * @date 2019/11/22 16:44
 **/
@RestController()
@RequestMapping("/consign/beyondPrice")
public class BeyondPetNumController {
    @Resource
    BeyondPetNumService beyondPetNumService;

    @GetMapping()
    public JsonResult getByType(@RequestParam("transportType") Integer transportType, @SessionAttribute("staff") Staff staff) {
        if (transportType == null) {
            return JsonResult.error(Code.PARAM_ERROR, "参数为空");
        }
        return JsonResult.success(beyondPetNumService.getByStationNoAndType(staff.getStation().getStationNo(), transportType));
    }

    @PutMapping()
    public JsonResult saveOrUpdate(@RequestBody BeyondPetNum beyondPetNum, @SessionAttribute("staff") Staff staff) {
        if (beyondPetNum == null) {
            return JsonResult.error(Code.PARAM_ERROR, "请求参数为空！");
        }
        // 设置参数
        beyondPetNum.setStation(staff.getStation());
        return JsonResult.success(beyondPetNumService.saveOrUpdate(beyondPetNum));
    }
}
